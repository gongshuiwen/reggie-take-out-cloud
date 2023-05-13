package org.example.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.reggie.exception.MyException;
import org.example.reggie.service.CategoryService;
import org.example.reggie.service.DishService;
import org.example.reggie.service.SetmealService;
import org.example.reggie.entity.Category;
import org.example.reggie.entity.Dish;
import org.example.reggie.entity.Setmeal;
import org.example.reggie.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) throws MyException{
        // 查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Dish> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Dish::getCategoryId, id);
        if (dishService.count(wrapper1) > 0) {
            throw new MyException("当前分类存在关联的菜品，无法删除！");
        };

        // 查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(Setmeal::getCategoryId, id);
        if (setmealService.count(wrapper2) > 0) {
            throw new MyException("当前分类存在关联的套餐，无法删除！");
        };

        // 删除分类
//        Category category = this.getById(id);
        this.removeById(id);
    }
}
