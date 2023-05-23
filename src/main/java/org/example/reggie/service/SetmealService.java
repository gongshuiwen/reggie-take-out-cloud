package org.example.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.reggie.entity.Setmeal;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    Setmeal getByIdWithSetmealDishes(Long id);

    Page<Setmeal> pageWithCategoryName(Long pageNum, Long pageSize, String name);

    List<Setmeal> listByCategoryIdWithSetmealDishes(Long categoryId, Integer status);

    @Transactional
    Boolean saveWithSetmealDishes(Setmeal setmeal);

    @Transactional
    Boolean updateWithSetmealDishes(Setmeal setmeal);

    @Transactional
    Boolean removeWithSetmealDishes(List<Long> ids);

    @Transactional
    Boolean changeStatusByBatchIds(List<Long> ids, Long status);
}
