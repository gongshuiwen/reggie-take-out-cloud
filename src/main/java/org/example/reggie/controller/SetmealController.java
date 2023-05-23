package org.example.reggie.controller;

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

    @PostMapping
    public R<Boolean> create(@RequestBody Setmeal setmeal) {
        return R.success(setmealService.saveWithSetmealDishes(setmeal));
    }

    @PutMapping
    public R<Boolean> update(@RequestBody Setmeal setmeal) {
        return R.success(setmealService.updateWithSetmealDishes(setmeal));
    }

    @DeleteMapping
    public R<Boolean> delete(@RequestParam List<Long> ids) {
        return R.success(setmealService.removeWithSetmealDishes(ids));
    }

    @GetMapping("/dish/{id}")
    public R<List<SetmealDish>> getSetmealDishes(@PathVariable Long id) {
        return R.success(setmealService.getByIdWithSetmealDishes(id).getSetmealDishes());
    }

    @GetMapping("/page")
    public R<Page<Setmeal>> page(@RequestParam Long page, @RequestParam Long pageSize,
                                 @RequestParam(required = false) String name) {
        return R.success(setmealService.pageWithCategoryName(page, pageSize, name));
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(@RequestParam Long categoryId, @RequestParam Integer status) {
        return R.success(setmealService.listByCategoryIdWithSetmealDishes(categoryId, status));
    }

    @PostMapping("/status/{status}")
    public R<Boolean> changeStatus(@PathVariable Long status, @RequestParam List<Long> ids) {
        return R.success(setmealService.changeStatusByBatchIds(ids, status));
    }
}
