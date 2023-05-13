package org.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.entity.Dish;
import org.example.reggie.service.CategoryService;
import org.example.reggie.common.R;
import org.example.reggie.entity.Category;
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
    public R<String> create(@RequestBody Category category) {
        categoryService.save(category);
        return R.success("");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return R.success("");
    }

    @DeleteMapping
    public R<String> delete(@RequestParam Long ids) {
        categoryService.remove(ids);
        return R.success("");
    }

    @GetMapping("/page")
    public R<Page<Category>> page(@RequestParam int page, @RequestParam int pageSize) {
        Page<Category> page1 = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Category::getSort);
        categoryService.page(page1, wrapper);
        return R.success(page1);
      }

    @GetMapping("/list")
    public R<List<Category>> list(@RequestParam @Nullable Integer type) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .eq(type != null, Category::getType, type)
                .orderByAsc(Category::getSort)
                .orderByDesc(Category::getUpdateTime);
        return R.success(categoryService.list(wrapper));
    }
}
