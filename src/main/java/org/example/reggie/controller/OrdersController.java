package org.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.BaseContext;
import org.example.reggie.common.R;
import org.example.reggie.entity.Orders;
import org.example.reggie.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/userPage")
    public R<Page<Orders>> userPage(Long page, Long pageSize) {
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getUserId, BaseContext.getCurrentUserId());
        return R.success(ordersService.page(pageInfo, wrapper));
    }

    @GetMapping("/page")
    public R<Page<Orders>> page(Long page, Long pageSize) {
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Orders::getOrderTime);
        return R.success(ordersService.page(pageInfo, wrapper));
    }

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders order) {
        ordersService.submitOrder(order, BaseContext.getCurrentUserId());
        return R.success("");
    }
}
