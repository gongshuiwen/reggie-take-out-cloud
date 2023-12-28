package org.example.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.reggie.common.protocal.R;
import org.example.reggie.entity.Setmeal;
import org.example.reggie.entity.SetmealDish;
import org.example.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "套餐接口")
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Operation(summary = "查询单个套餐")
    @GetMapping("/{id}")
    public R<Setmeal> get(@Parameter(description = "套餐ID") @PathVariable Long id) {
        return R.success(setmealService.getByIdWithSetmealDishes(id));
    }

    @Operation(summary = "创建套餐")
    @PostMapping
    public R<Boolean> create(@Parameter(description = "套餐信息") @RequestBody Setmeal setmeal) {
        return R.success(setmealService.saveWithSetmealDishes(setmeal));
    }

    @Operation(summary = "更新套餐")
    @PutMapping
    public R<Boolean> update(@Parameter(description = "套餐信息") @RequestBody Setmeal setmeal) {
        return R.success(setmealService.updateWithSetmealDishes(setmeal));
    }

    @Operation(summary = "删除套餐")
    @DeleteMapping
    public R<Boolean> delete(@Parameter(description = "套餐ID集合") @RequestParam List<Long> ids) {
        return R.success(setmealService.removeWithSetmealDishes(ids));
    }

    @Operation(summary = "获取套餐的菜品明细")
    @GetMapping("/dish/{id}")
    public R<List<SetmealDish>> getSetmealDishes(@Parameter(description = "套餐ID") @PathVariable Long id) {
        return R.success(setmealService.getByIdWithSetmealDishes(id).getSetmealDishes());
    }

    @Operation(summary = "套餐分页查询")
    @GetMapping("/page")
    public R<Page<Setmeal>> page(
            @Parameter(description = "页码") @RequestParam Long page,
            @Parameter(description = "页大小") @RequestParam Long pageSize,
            @Parameter(description = "套餐名称") @RequestParam(required = false) String name) {
        return R.success(setmealService.pageWithCategoryName(page, pageSize, name));
    }

    @Operation(summary = "套餐列表查询")
    @GetMapping("/list")
    public R<List<Setmeal>> list(
            @Parameter(description = "类别ID") @RequestParam Long categoryId,
            @Parameter(description = "套餐状态") @RequestParam Integer status) {
        return R.success(setmealService.listByCategoryIdWithSetmealDishes(categoryId, status));
    }

    @Operation(summary = "变更套餐状态")
    @PostMapping("/status/{status}")
    public R<Boolean> changeStatus(
            @Parameter(description = "套餐状态") @PathVariable Long status,
            @Parameter(description = "套餐ID集合") @RequestParam List<Long> ids) {
        return R.success(setmealService.changeStatusByBatchIds(ids, status));
    }
}
