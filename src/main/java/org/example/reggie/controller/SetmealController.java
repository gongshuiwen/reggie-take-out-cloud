package org.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.reggie.common.R;
import org.example.reggie.entity.Setmeal;
import org.example.reggie.entity.SetmealDish;
import org.example.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @GetMapping("/{id}")
    public R<Setmeal> get(@PathVariable Long id) {
        return R.success(setmealService.getByIdWithSetmealDishes(id));
    }

    @GetMapping("/dish/{id}")
    public R<List<SetmealDish>> getSetmealDishes(@PathVariable Long id) {
        return R.success(setmealService.getByIdWithSetmealDishes(id).getSetmealDishes());
    }

    @GetMapping("/page")
    public R<Page<Setmeal>> page(int page, int pageSize, String name) {
        return R.success(setmealService.pageWithCategoryName(page, pageSize, name));
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Long categoryId, Integer status) {
        return R.success(setmealService.listByCategoryIdWithSetmealDishes(categoryId, status));
    }

    @PostMapping
    public R<String> create(@RequestBody Setmeal setmeal) {
        setmealService.saveWithSetmealDishes(setmeal);
        return R.success("");
    }

    @PutMapping
    public R<String> update(@RequestBody Setmeal setmeal) {
        setmealService.updateWithSetmealDishes(setmeal);
        return R.success("");
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        setmealService.removeWithSetmealDishes(ids);
        return R.success("");
    }

    @PostMapping("/status/{status}")
    public R<String> changeStatus(@PathVariable int status, @RequestParam List<Long> ids) {
        setmealService.changeStatusByBatchIds(ids, status);
        return R.success("");
    }
}
