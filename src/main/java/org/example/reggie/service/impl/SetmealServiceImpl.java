package org.example.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.reggie.entity.Category;
import org.example.reggie.entity.SetmealDish;
import org.example.reggie.exception.MyException;
import org.example.reggie.service.CategoryService;
import org.example.reggie.service.SetmealDishService;
import org.example.reggie.service.SetmealService;
import org.example.reggie.entity.Setmeal;
import org.example.reggie.mapper.SetmealMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    public static final String SETMEALS_LIST_BY_CATEGORY_CACHE_KEY = "setmealsListByCategoryCache";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Setmeal getByIdWithSetmealDishes(Long id) {
        Setmeal setmeal = this.getById(id);
        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId, setmeal.getId());
        setmeal.setSetmealDishes(setmealDishService.list(wrapper));
        return setmeal;
    }

    @Override
    public Page<Setmeal> pageWithCategoryName(Long pageNum, Long pageSize, String name) {
        Page<Setmeal> page1 = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .like(!(name == null || name.equals("")), Setmeal::getName, name)
                .orderByDesc(Setmeal::getUpdateTime);
        this.page(page1, wrapper);

        // 获取 CategoryIds
        Set<Long> categoryIds = page1.getRecords().stream()
            .map(Setmeal::getCategoryId)
            .collect(Collectors.toSet());

        //获取 categoryNames
        HashMap<Long, String> categoryNames = new HashMap<>();
        if (!categoryIds.isEmpty()) {
            for (Category category : categoryService.listByIds(categoryIds)) {
                if (category != null){
                    categoryNames.put(category.getId(), category.getName());
                }
            }
        }

        for (Setmeal setmeal: page1.getRecords()) {
            Long categoryId = setmeal.getCategoryId();
            setmeal.setCategoryName(categoryNames.getOrDefault(categoryId, ""));
        }

        return page1;
    }

    @Override
    public List<Setmeal> listByCategoryIdWithSetmealDishes(Long categoryId, Integer status) {

        // 查询缓存
        List<Setmeal> setmeals = null;
        String redisKey = SETMEALS_LIST_BY_CATEGORY_CACHE_KEY + ":" + categoryId.toString();
        if ((setmeals = (List<Setmeal>) redisTemplate.opsForValue().get(redisKey)) != null) {
            return setmeals;
        }

        // 查询数据库
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(Setmeal::getCategoryId, categoryId)
                .eq(Setmeal::getStatus, status)
                .orderByDesc(Setmeal::getUpdateTime);
        setmeals = this.list(wrapper);

        // 放入缓存
        redisTemplate.opsForValue().set(redisKey, setmeals, 60, TimeUnit.MINUTES);
        return setmeals;
    }

    @Override
    public Boolean saveWithSetmealDishes(Setmeal setmeal) {
        this.save(setmeal);
        setmeal.getSetmealDishes().forEach(setmealDish -> setmealDish.setSetmealId(setmeal.getId()));
        setmealDishService.saveBatch(setmeal.getSetmealDishes());

        // 删除所属类别的套餐缓存
        redisTemplate.delete(SETMEALS_LIST_BY_CATEGORY_CACHE_KEY + ":" + setmeal.getCategoryId().toString());

        return Boolean.TRUE;
    }

    @Override
    public Boolean updateWithSetmealDishes(Setmeal setmeal) {
        // 获取更新前的 setmeal 数据
        Setmeal oldSetmeal = this.getById(setmeal.getId());

        // 更新 setmeal
        this.updateById(setmeal);

        // 移除已经不存在的 setmealDishes
        Set<Long> newSetmealDishIds = setmeal.getSetmealDishes().stream()
                .map(SetmealDish::getId).collect(Collectors.toSet());
        setmealDishService.removeByIds(
                setmealDishService.listBySetmealId(setmeal.getId()).stream()
                        .map(SetmealDish::getId)
                        .filter(id -> !newSetmealDishIds.contains(id)).collect(Collectors.toSet()));

        // 更新或创建 setmealDishes
        setmeal.getSetmealDishes().stream()
                .filter(setmealDish -> setmealDish.getSetmealId() == null)
                .forEach(setmealDish -> setmealDish.setSetmealId(setmeal.getId()));
        setmealDishService.saveOrUpdateBatch(setmeal.getSetmealDishes());

        // 如果该套餐的类别被修改，则清除修改前和修改后套餐类别的套餐缓存
        if (!Objects.equals(setmeal.getCategoryId(), oldSetmeal.getCategoryId())) {
            List<String> redisKeys = new ArrayList<>(2);
            redisKeys.add(SETMEALS_LIST_BY_CATEGORY_CACHE_KEY + ":" + setmeal.getCategoryId().toString());
            redisKeys.add(SETMEALS_LIST_BY_CATEGORY_CACHE_KEY + ":" + oldSetmeal.getCategoryId().toString());
            redisTemplate.delete(redisKeys);
        }

        return Boolean.TRUE;
    }

    @Override
    public Boolean removeWithSetmealDishes(List<Long> ids) {
        if (this.count(new LambdaQueryWrapper<Setmeal>().in(Setmeal::getId, ids).eq(Setmeal::getStatus, 1)) > 0) {
            throw new MyException("无法删除启售状态的套餐！");
        };

        setmealDishService.remove(new LambdaQueryWrapper<SetmealDish>().in(SetmealDish::getDishId, ids));
        return this.removeByIds(ids);
    }

    public Boolean changeStatusByBatchIds(List<Long> ids, Long status) {
        this.update(new LambdaUpdateWrapper<Setmeal>()
                .in(Setmeal::getId, ids)
                .eq(Setmeal::getStatus, 1 - status)
                .set(Setmeal::getStatus, status));

        // 批量删除相关套餐类别的套餐缓存
        List<String> redisKeys = this.listByIds(ids).stream()
                .map(setmeal -> SETMEALS_LIST_BY_CATEGORY_CACHE_KEY + ":" + setmeal.getCategoryId().toString())
                .collect(Collectors.toList());
        redisTemplate.delete(redisKeys);

        return Boolean.TRUE;
    }
}
