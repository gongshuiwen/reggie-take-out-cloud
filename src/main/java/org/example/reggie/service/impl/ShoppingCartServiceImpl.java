package org.example.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.reggie.common.BaseContext;
import org.example.reggie.entity.Dish;
import org.example.reggie.entity.Setmeal;
import org.example.reggie.entity.ShoppingCart;
import org.example.reggie.exception.MyException;
import org.example.reggie.mapper.ShoppingCartMapper;
import org.example.reggie.service.DishService;
import org.example.reggie.service.SetmealService;
import org.example.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private DishService dishService;

    @Override
    public List<ShoppingCart> listUserShoppingCart(Long userId) {
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentUserId())
                .orderByAsc(ShoppingCart::getCreateTime);
        return this.list(wrapper);
    }

    @Override
    public Boolean addUserShoppingCart(Long userId, ShoppingCart shoppingCart) {
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(ShoppingCart::getUserId, userId)
                .eq(shoppingCart.getDishId() != null, ShoppingCart::getDishId, shoppingCart.getDishId())
                .eq(shoppingCart.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCart.getSetmealId())
                .eq(shoppingCart.getDishFlavor() != null, ShoppingCart::getDishFlavor, shoppingCart.getDishFlavor());

        ShoppingCart shoppingCart1 = this.getOne(wrapper);
        if (shoppingCart1 != null) {
            shoppingCart = new ShoppingCart();
            shoppingCart.setId(shoppingCart.getId());
            shoppingCart.setNumber(shoppingCart.getNumber() + 1);
            return this.updateById(shoppingCart);
        } else {
            shoppingCart.setUserId(userId);
            return this.save(shoppingCart);
        }
    }

    @Override
    public Boolean subUserShoppingCart(Long userId, ShoppingCart shoppingCart) {
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(ShoppingCart::getUserId, userId)
                .eq(shoppingCart.getDishId() != null, ShoppingCart::getDishId, shoppingCart.getDishId())
                .eq(shoppingCart.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCart.getSetmealId());

        ShoppingCart shoppingCart1 = this.getOne(wrapper);
        if (shoppingCart1 != null) {
            if (shoppingCart1.getNumber() <= 1) {
                return this.removeById(shoppingCart1.getId());
            } else {
                ShoppingCart shoppingCartUpdate = new ShoppingCart();
                shoppingCartUpdate.setId(shoppingCart1.getId());
                shoppingCartUpdate.setNumber(shoppingCart1.getNumber() - 1);
                return this.updateById(shoppingCartUpdate);
            }
        } else {
            throw new MyException("从购物车中删除了不存在项目！");
        }
    }

    @Override
    public Boolean cleanUserShoppingCart(Long userId) {
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, userId);
        return this.remove(wrapper);
    }

    /**
     * 为 shoppingCart 对象计算金额
     * @param shoppingCart: 购物车明细实例
     * @throws MyException: 购物车实例未绑定菜品或套餐
     */
    private void setShoppingCartAmount(ShoppingCart shoppingCart) throws MyException {

        if (shoppingCart.getSetmealId() != null) {
            Setmeal setmeal = setmealService.getById(shoppingCart.getSetmealId());
            shoppingCart.setAmount(setmeal.getPrice().multiply(new BigDecimal(shoppingCart.getNumber()))
                    .divide(new BigDecimal(100), RoundingMode.HALF_UP));
        } else if (shoppingCart.getDishId() != null) {
            Dish dish = dishService.getByIdWithFlavors(shoppingCart.getDishId());
            shoppingCart.setAmount(dish.getPrice().multiply(new BigDecimal(shoppingCart.getNumber()))
                    .divide(new BigDecimal(100), RoundingMode.HALF_UP));
        } else {
            throw new MyException("购物车实例未绑定菜品或套餐！");
        }
    }
}
