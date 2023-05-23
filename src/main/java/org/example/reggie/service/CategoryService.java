package org.example.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.reggie.entity.Category;

import javax.annotation.Nullable;
import java.util.List;

public interface CategoryService extends IService<Category> {

    Page<Category> pageOrderBySort(Long pageNum, Long pageSize);

    List<Category> listWithTypeOrderBySort(@Nullable Integer type);
}
