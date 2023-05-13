package org.example.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.R;
import org.example.reggie.entity.Dish;
import org.example.reggie.service.CategoryService;
import org.example.reggie.service.DishFlavorService;
import org.example.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishFlavorService dishFlavorService;

    @GetMapping("/{id}")
    public R<Dish> get(@PathVariable Long id) {
        return R.success(dishService.getByIdWithFlavors(id));
    }

    @GetMapping("/page")
    public R<Page<Dish>> page(int page, int pageSize, String name) {
        return R.success(dishService.pageWithCategoryName(page, pageSize, name));
    }

    @GetMapping("/list")
    public R<List<Dish>> list(Long categoryId, Integer status) {
        return R.success(dishService.listByCategoryIdWithFlavors(categoryId, status));
    }

    @PostMapping
    public R<String> create(@RequestBody Dish dish) {
        dishService.saveWithFlavors(dish);
        return R.success("");
    }

    @PutMapping
    public R<String> update(@RequestBody Dish dish) {
        dishService.updateWithFlavors(dish);
        return R.success("");
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        dishService.removeByIdsWithFlavors(ids);
        return R.success("");
    }

    @PostMapping("/status/{status}")
    public R<String> changeStatus(@PathVariable int status, @RequestParam List<Long> ids) {
        dishService.changeStatusByBatchIds(ids, status);
        return R.success("");
    }
}
