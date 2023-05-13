package org.example.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.reggie.entity.SetmealDish;

import java.util.List;

public interface SetmealDishService extends IService<SetmealDish> {

    public List<SetmealDish> listBySetmealId(Long setmealId);
}
