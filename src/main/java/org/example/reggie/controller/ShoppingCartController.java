package org.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.BaseContext;
import org.example.reggie.common.R;
import org.example.reggie.entity.Dish;
import org.example.reggie.entity.Setmeal;
import org.example.reggie.entity.ShoppingCart;
import org.example.reggie.exception.MyException;
import org.example.reggie.service.DishService;
import org.example.reggie.service.SetmealService;
import org.example.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private DishService dishService;

    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentUserId())
                .orderByAsc(ShoppingCart::getCreateTime);
        return R.success(shoppingCartService.list(wrapper));
    }

    @PostMapping("/add")
    public R<String> add(@RequestBody ShoppingCart shoppingCart) {
        Long userId = BaseContext.getCurrentUserId();
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(ShoppingCart::getUserId, userId)
                .eq(shoppingCart.getDishId() != null, ShoppingCart::getDishId, shoppingCart.getDishId())
                .eq(shoppingCart.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCart.getSetmealId())
                .eq(shoppingCart.getDishFlavor() != null, ShoppingCart::getDishFlavor, shoppingCart.getDishFlavor());
        ShoppingCart shoppingCart1 = shoppingCartService.getOne(wrapper);
        if (shoppingCart1 != null) {
            shoppingCart = shoppingCart1;
            shoppingCart.setNumber(shoppingCart.getNumber() + 1);
        } else {
            shoppingCart.setUserId(BaseContext.getCurrentUserId());
        }

        shoppingCartService.saveOrUpdate(shoppingCart);
        return R.success("");
    }

    @PostMapping("/sub")
    public R<String> sub(@RequestBody ShoppingCart shoppingCartDto) {
        Long userId = BaseContext.getCurrentUserId();
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(ShoppingCart::getUserId, userId)
                .eq(shoppingCartDto.getDishId() != null, ShoppingCart::getDishId, shoppingCartDto.getDishId())
                .eq(shoppingCartDto.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCartDto.getSetmealId());
        ShoppingCart shoppingCart = shoppingCartService.getOne(wrapper);
        if (shoppingCart != null) {
            if (shoppingCart.getNumber() <= 1) {
                shoppingCartService.removeById(shoppingCart.getId());
            } else {
                shoppingCart.setNumber(shoppingCart.getNumber() - 1);
                shoppingCartService.updateById(shoppingCart);
            }
        } else {
            return R.error("从购物车中删除了不存在项目！");
        }
        return R.success("");
    }

    @DeleteMapping("/clean")
    public R<String> clean() {
        Long userId = BaseContext.getCurrentUserId();
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, userId);
        shoppingCartService.remove(wrapper);
        return R.success("");
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

