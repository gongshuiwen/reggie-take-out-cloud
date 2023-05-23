package org.example.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.R;
import org.example.reggie.entity.Category;
import org.example.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public R<Category> get(@PathVariable Long id) {
        return R.success(categoryService.getById(id));
    }

    @PostMapping
    public R<Category> create(@RequestBody Category category) {
        categoryService.save(category);
        return R.success(category);
    }

    @PutMapping
    public R<Boolean> update(@RequestBody Category category) {
        return R.success(categoryService.updateById(category));
    }

    @DeleteMapping
    public R<Boolean> delete(@RequestParam List<Long> ids) {
        return R.success(categoryService.removeByIds(ids));
    }

    @GetMapping("/page")
    public R<Page<Category>> page(@RequestParam Long page, @RequestParam Long pageSize) {
        return R.success(categoryService.pageOrderBySort(page, pageSize));
    }

    @GetMapping("/list")
    public R<List<Category>> list(@RequestParam @Nullable Integer type) {
        return R.success(categoryService.listWithTypeOrderBySort(type));
    }
}
