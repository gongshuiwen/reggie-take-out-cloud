package org.example.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.R;
import org.example.reggie.entity.Dish;
import org.example.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping("/{id}")
    public R<Dish> get(@PathVariable Long id) {
        return R.success(dishService.getByIdWithFlavors(id));
    }

    @PostMapping
    public R<Dish> create(@RequestBody Dish dish) {
        return R.success(dishService.saveWithFlavors(dish));
    }

    @PutMapping
    public R<Boolean> update(@RequestBody Dish dish) {
        return R.success(dishService.updateWithFlavors(dish));
    }

    @DeleteMapping
    public R<Boolean> delete(@RequestParam List<Long> ids) {
        return R.success(dishService.removeByIdsWithFlavors(ids));
    }

    @GetMapping("/page")
    public R<Page<Dish>> page(@RequestParam Long page, @RequestParam Long pageSize,
                              @RequestParam(required = false) String name) {
        return R.success(dishService.pageWithCategoryName(page, pageSize, name));
    }

    @GetMapping("/list")
    public R<List<Dish>> list(@RequestParam Long categoryId, @RequestParam Integer status) {
        return R.success(dishService.listByCategoryIdWithFlavors(categoryId, status));
    }

    @PostMapping("/status/{status}")
    public R<Boolean> changeStatus(@PathVariable Integer status, @RequestParam List<Long> ids) {
        return R.success(dishService.changeStatusByBatchIds(ids, status));
    }
}
