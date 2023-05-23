package org.example.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.reggie.entity.Dish;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DishService extends IService<Dish> {

    Dish getByIdWithFlavors(Long id);

    Page<Dish> pageWithCategoryName(long current, long size, String name);

    List<Dish> listByCategoryIdWithFlavors(Long categoryId, Integer status);

    @Transactional
    Dish saveWithFlavors(Dish dish);

    @Transactional
    Boolean updateWithFlavors(Dish dish);

    @Transactional
    Boolean removeByIdsWithFlavors(List<Long> ids);

    @Transactional
    Boolean changeStatusByBatchIds(List<Long> ids, int status);
}
