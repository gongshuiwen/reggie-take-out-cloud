package org.example.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.reggie.entity.Setmeal;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    public Setmeal getByIdWithSetmealDishes(Long id);

    public Page<Setmeal> pageWithCategoryName(long current, long size, String name);

    public List<Setmeal> listByCategoryIdWithSetmealDishes(Long categoryId, Integer status);

    @Transactional
    public void saveWithSetmealDishes(Setmeal setmeal);

    @Transactional
    public void updateWithSetmealDishes(Setmeal setmeal);

    @Transactional
    public void removeWithSetmealDishes(List<Long> ids);

    @Transactional
    public void changeStatusByBatchIds(List<Long> ids, int status);
}
