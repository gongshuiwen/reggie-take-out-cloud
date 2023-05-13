package org.example.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.reggie.entity.Category;
import org.example.reggie.entity.DishFlavor;
import org.example.reggie.entity.SetmealDish;
import org.example.reggie.exception.MyException;
import org.example.reggie.service.CategoryService;
import org.example.reggie.service.DishFlavorService;
import org.example.reggie.service.DishService;
import org.example.reggie.entity.Dish;
import org.example.reggie.mapper.DishMapper;
import org.example.reggie.service.SetmealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    public static final String DISHES_LIST_BY_CATEGORY_CACHE_KEY = "dishesListByCategoryCache";

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Override
    public Dish getByIdWithFlavors(Long id) {
        Dish dish = this.getById(id);
        Category category = categoryService.getById(dish.getCategoryId());
        if (category != null) {
            dish.setCategoryName(category.getName());
        }
        dish.setFlavors(dishFlavorService.getFlavorsByDishId(dish.getId()));
        return dish;
    }

    @Override
    public Page<Dish> pageWithCategoryName(long current, long size, String name) {
        Page<Dish> page1 = new Page<>(current, size);
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .like(!(name == null || name.equals("")), Dish::getName, name)
                .orderByAsc(Dish::getSort)
                .orderByDesc(Dish::getUpdateTime);
        this.page(page1, wrapper);

        // 获取 CategoryIds
        Set<Long> categoryIds = new HashSet<>();
        for (Dish dish: page1.getRecords()) {
            categoryIds.add(dish.getCategoryId());
        }

//        Set<Long> categoryIds = page1.getRecords().stream()
//        .map(Dish::getCategoryId).collect(Collectors.toSet());

        //获取 categoryNames
        HashMap<Long, String> categoryNames = new HashMap<>();
        for (Category category : categoryService.listByIds(categoryIds)) {
            if (category != null){
                categoryNames.put(category.getId(), category.getName());
            }
        }

        for (Dish dish: page1.getRecords()) {
            // 获取 categoryId
            Long categoryId = dish.getCategoryId();
            String categoryName = "";

            // 从 categoryNames 获取
            if (categoryNames.containsKey(categoryId)) {
                categoryName = categoryNames.get(categoryId);
            }

            // 设置 categoryName
            dish.setCategoryName(categoryName);
        }

        return page1;
    }

    @Override
    @Cacheable(value = DISHES_LIST_BY_CATEGORY_CACHE_KEY, key="#categoryId", unless = "")
    public List<Dish> listByCategoryIdWithFlavors(Long categoryId, Integer status) {
        // 获取 dishes
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(Dish::getCategoryId, categoryId)
                .eq(Dish::getStatus, status)
                .orderByAsc(Dish::getSort);
        List<Dish> dishes = this.list(wrapper);

        if (dishes != null && dishes.size() > 0) {
            // 批量加载 dishFlavors
            Set<Long> dishIds = dishes.stream().map(Dish::getId).collect(Collectors.toSet());
            HashMap<Long, List<DishFlavor>> dishFlavors = new HashMap<>();
            LambdaQueryWrapper<DishFlavor> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.in(DishFlavor::getDishId, dishIds);
            dishFlavorService.list(wrapper1).forEach(dishFlavor -> {
                Long dishId = dishFlavor.getDishId();
                if (!dishFlavors.containsKey(dishId)) {
                    dishFlavors.put(dishId, new ArrayList<>());
                }
                dishFlavors.get(dishId).add(dishFlavor);
            });

            // 填充 dishFlavors 至 dish 中
            dishes.stream()
                    .filter(dish -> dishFlavors.containsKey(dish.getId()))
                    .forEach(dish -> dish.setFlavors(dishFlavors.get(dish.getId())));
        }

        return dishes;
    }

    /**
     * 新增菜品与菜品口味信息
     * 缓存删除策略：需要删除菜品类别对应的菜品缓存
     * @param dish 需要新增的菜品与菜品口味信息
     */
    @Override
    @CacheEvict(value = DISHES_LIST_BY_CATEGORY_CACHE_KEY, key="#dish.categoryId")
    public void saveWithFlavors(Dish dish) {
        this.save(dish);
        List<DishFlavor> flavors = dish.getFlavors();
        for (DishFlavor flavor: flavors) {
            flavor.setDishId(dish.getId());
        }
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 更新菜品与菜品口味信息
     * 缓存删除策略：
     * - 当菜品的类别不变时，需要删除菜品类别对应的菜品缓存；
     * - 当菜品的类别改变时，需要删除源菜品类别和目标菜品类别的菜品缓存。
     * @param dish 需要更新的菜品与菜品口味信息
     * @return 更新前的菜品信息（不包含菜品口味信息），用于删除更新前菜品类别的菜品缓存
     */
    @Override
    @Caching(evict = {
            @CacheEvict(value = DISHES_LIST_BY_CATEGORY_CACHE_KEY, key="#dish.categoryId"),
            @CacheEvict(value = DISHES_LIST_BY_CATEGORY_CACHE_KEY, key="#result.categoryId")
    })
    public Dish updateWithFlavors(Dish dish) {
        Dish oldDish = this.getById(dish.getId());
        this.updateById(dish);

        // 移除不存在的 dishFlavor
        HashSet<Long> newDishFlavorIds = new HashSet<>();
        dish.getFlavors().forEach(dishFlavor -> newDishFlavorIds.add(dishFlavor.getId()));
        HashSet<Long> dishFlavorIdsToMove = new HashSet<>();
        for (DishFlavor dishFlavor: dishFlavorService.getFlavorsByDishId(dish.getId())) {
            Long dishFlavorId = dishFlavor.getId();
            if (!newDishFlavorIds.contains(dishFlavorId)) {
                dishFlavorIdsToMove.add(dishFlavorId);
            }
        }
        dishFlavorService.removeByIds(dishFlavorIdsToMove);

        // 创建或者更新现有的 DishFlavors
        dish.getFlavors().stream()
                .filter(dishFlavor -> dishFlavor.getDishId() == null)
                .forEach(dishFlavor -> dishFlavor.setDishId(dish.getId()));
        dishFlavorService.saveOrUpdateBatch(dish.getFlavors());

        return oldDish;
    }

    /**
     * 批量删除菜品以及关联的菜品口味信息
     * 缓存删除策略：
     * - 删除每个菜品对应的类别的菜品缓存（@CacheEvict 如何实现？）
     * @param ids 需要删除的菜品信息的 id 集合
     */
    @Override
    public void removeByIdsWithFlavors(List<Long> ids) {
        // 验证是否关联套餐
        if (setmealDishService.count(new LambdaQueryWrapper<SetmealDish>().in(SetmealDish::getDishId, ids)) > 0) {
            throw new MyException("存在已关联套餐的菜品，请先删除相关套餐！");
        }

        // 验证是否为停售状态
        if (this.count(new LambdaQueryWrapper<Dish>().in(Dish::getId, ids).eq(Dish::getStatus, 1)) > 0) {
            throw new MyException("无法删除启售状态的菜品！");
        }

        // 删除菜品和口味信息
        dishFlavorService.remove(new LambdaQueryWrapper<DishFlavor>().in(DishFlavor::getDishId, ids));
        this.removeByIds(ids);
    }

    /**
     * 批量更新菜品的启售和停售状态
     * 缓存删除策略：
     * - 删除每个菜品对应的类别的菜品缓存
     * @param ids 需要删除的菜品信息的 id 集合
     * @param status 1 启售，0 停售
     */
    @Override
    @CacheEvict(value = "dishesListByCategoryCache")
    public void changeStatusByBatchIds(List<Long> ids, int status) {
        this.update(new LambdaUpdateWrapper<Dish>()
                .in(Dish::getId, ids)
                .eq(Dish::getStatus, 1 - status)
                .set(Dish::getStatus, status));

        // 手动删除相关菜品类别的菜品缓存
        this.listByIds(ids).forEach(
           dish -> {
               Cache cache;
               if ((cache = cacheManager.getCache(DISHES_LIST_BY_CATEGORY_CACHE_KEY)) != null) {
                   cache.evictIfPresent(dish.getCategoryId());
               }
           }
        );
    }
}
