package org.example.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.reggie.entity.*;
import org.example.reggie.user.entity.*;
import org.example.reggie.mapper.OrdersMapper;
import org.example.reggie.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private UserService userService;

    @Override
    public Boolean submitOrder(Orders order) {
        Long userId = order.getUserId();
        User user = userService.getById(userId);
        AddressBook addressBook = addressBookService.getById(order.getAddressBookId());
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(
                new LambdaQueryWrapper<ShoppingCart>().eq(ShoppingCart::getUserId, userId));

        // 保存订单信息
        order.setStatus(1);
        order.setNumber("1");
        order.setUserId(userId);
        order.setUserName(user.getName());
        order.setConsignee(addressBook.getConsignee());
        order.setPhone(addressBook.getPhone());
        order.setAddress(addressBook.getDetail());
        order.setOrderTime(LocalDateTime.now());
        order.setCheckoutTime(LocalDateTime.now());
        order.setAmount(shoppingCarts.stream()
                .map(shoppingCart -> shoppingCart.getAmount().multiply(new BigDecimal(shoppingCart.getNumber())))
                .reduce(BigDecimal::add).get());
        this.save(order);

        // 将购物车内容保存为订单明细
        List<OrderDetail> orderDetails = shoppingCarts.stream().map(shoppingCart -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order.getId());
            orderDetail.setSetmealId(shoppingCart.getSetmealId());
            orderDetail.setDishId(shoppingCart.getDishId());
            orderDetail.setImage(shoppingCart.getImage());
            orderDetail.setName(shoppingCart.getName());
            orderDetail.setDishFlavor(shoppingCart.getDishFlavor());
            orderDetail.setNumber(shoppingCart.getNumber());
            orderDetail.setAmount(shoppingCart.getAmount());
            return orderDetail;
        }).collect(Collectors.toList());
        orderDetailService.saveBatch(orderDetails);

        // 删除购物车内容
        shoppingCartService.removeByIds(shoppingCarts.stream().map(ShoppingCart::getId).collect(Collectors.toSet()));

        return Boolean.TRUE;
    }

    @Override
    public Page<Orders> pageWithUserIdOrderByOrderTimeDesc(Long pageNum, Long pageSize, Long userId) {
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Orders::getOrderTime);
        wrapper.eq(userId != null, Orders::getUserId, userId);
        return this.page(new Page<>(pageNum, pageSize), wrapper);
    }
}
