package org.example.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.reggie.entity.Dish;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DishService extends IService<Dish> {

    public Dish getByIdWithFlavors(Long id);

    public Page<Dish> pageWithCategoryName(long current, long size, String name);

    public List<Dish> listByCategoryIdWithFlavors(Long categoryId, Integer status);

    @Transactional
    public void saveWithFlavors(Dish dish);

    @Transactional
    public Dish updateWithFlavors(Dish dish);

    @Transactional
    public void removeByIdsWithFlavors(List<Long> ids);

    @Transactional
    public void changeStatusByBatchIds(List<Long> ids, int status);
}
