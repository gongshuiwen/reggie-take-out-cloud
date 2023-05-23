package org.example.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.R;
import org.example.reggie.entity.Employee;
import org.example.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public R<Employee> get(@PathVariable Long id) {
        return R.success(employeeService.getById(id));
    }

    @PostMapping
    public R<Employee> create(@RequestBody Employee employee) {
        return R.success(employeeService.saveWithDefaultPassword(employee));
    }

    @PutMapping
    public R<Boolean> update(@RequestBody Employee employee) {
        return R.success(employeeService.updateById(employee));
    }

    @DeleteMapping
    public R<Boolean> delete(@RequestParam List<Long> ids) {
        return R.success(employeeService.removeByIds(ids));
    }

    @GetMapping("/page")
    public R<Page<Employee>> page(@RequestParam Long page, @RequestParam Long pageSize,
                                  @RequestParam(required = false) String name) {
        return R.success(employeeService.pageWithNameOrderByUpdateTime(page, pageSize, name));
    }
}
