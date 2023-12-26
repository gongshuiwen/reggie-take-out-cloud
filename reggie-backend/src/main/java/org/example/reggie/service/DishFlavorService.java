package org.example.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.reggie.entity.DishFlavor;

import java.util.List;

public interface DishFlavorService extends IService<DishFlavor> {

    public List<DishFlavor> getFlavorsByDishId(Long dishId);
}
