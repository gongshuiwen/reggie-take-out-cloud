package org.example.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.protocal.R;
import org.example.reggie.entity.Dish;
import org.example.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "菜品接口")
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Operation(summary = "查询单个菜品信息")
    @GetMapping("/{id}")
    public R<Dish> get(@PathVariable Long id) {
        return R.success(dishService.getByIdWithFlavors(id));
    }

    @Operation(summary = "创建菜品信息")
    @PostMapping
    public R<Dish> create(@RequestBody Dish dish) {
        return R.success(dishService.saveWithFlavors(dish));
    }

    @Operation(summary = "创建菜品信息")
    @PutMapping
    public R<Boolean> update(@RequestBody Dish dish) {
        return R.success(dishService.updateWithFlavors(dish));
    }

    @Operation(summary = "删除菜品信息")
    @DeleteMapping
    public R<Boolean> delete(@RequestParam List<Long> ids) {
        return R.success(dishService.removeByIdsWithFlavors(ids));
    }

    @Operation(summary = "分页查询菜品信息")
    @GetMapping("/page")
    public R<Page<Dish>> page(@RequestParam Long page, @RequestParam Long pageSize,
                              @RequestParam(required = false) String name) {
        return R.success(dishService.pageWithCategoryName(page, pageSize, name));
    }

    @Operation(summary = "列表查询菜品信息")
    @GetMapping("/list")
    public R<List<Dish>> list(@RequestParam Long categoryId, @RequestParam Integer status) {
        return R.success(dishService.listByCategoryIdWithFlavors(categoryId, status));
    }

    @Operation(summary = "变更菜品状态")
    @PostMapping("/status/{status}")
    public R<Boolean> changeStatus(@PathVariable Integer status, @RequestParam List<Long> ids) {
        return R.success(dishService.changeStatusByBatchIds(ids, status));
    }
}
