package org.example.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.protocal.R;
import org.example.reggie.entity.Category;
import org.example.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="类别接口")
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "查询单个类别信息")
    @GetMapping("/{id}")
    public R<Category> get(@PathVariable Long id) {
        return R.success(categoryService.getById(id));
    }

    @Operation(summary = "创建类别信息")
    @PostMapping
    public R<Category> create(@RequestBody Category category) {
        categoryService.save(category);
        return R.success(category);
    }

    @Operation(summary = "更新类别信息")
    @PutMapping
    public R<Boolean> update(@RequestBody Category category) {
        return R.success(categoryService.updateById(category));
    }

    @Operation(summary = "删除类别信息")
    @DeleteMapping
    public R<Boolean> delete(@RequestParam List<Long> ids) {
        return R.success(categoryService.removeByIds(ids));
    }

    @Operation(summary = "分页查询类别信息")
    @GetMapping("/page")
    public R<Page<Category>> page(@RequestParam Long page, @RequestParam Long pageSize) {
        return R.success(categoryService.pageOrderBySort(page, pageSize));
    }

    @Operation(summary = "列表查询类别信息")
    @GetMapping("/list")
    public R<List<Category>> list(@RequestParam @Nullable Integer type) {
        return R.success(categoryService.listWithTypeOrderBySort(type));
    }
}
