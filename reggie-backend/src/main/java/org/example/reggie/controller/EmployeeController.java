package org.example.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.protocal.R;
import org.example.reggie.entity.Employee;
import org.example.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "员工接口")
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Operation(summary = "查询单个员工信息")
    @GetMapping("/{id}")
    public R<Employee> get(@PathVariable Long id) {
        return R.success(employeeService.getById(id));
    }

    @Operation(summary = "创建员工信息")
    @PostMapping
    public R<Employee> create(@RequestBody Employee employee) {
        return R.success(employeeService.saveWithDefaultPassword(employee));
    }

    @Operation(summary = "更新员工信息")
    @PutMapping
    public R<Boolean> update(@RequestBody Employee employee) {
        return R.success(employeeService.updateById(employee));
    }

    @Operation(summary = "删除员工信息")
    @DeleteMapping
    public R<Boolean> delete(@RequestParam List<Long> ids) {
        return R.success(employeeService.removeByIds(ids));
    }

    @Operation(summary = "员工信息分页查询")
    @GetMapping("/page")
    public R<Page<Employee>> page(@RequestParam Long page, @RequestParam Long pageSize,
                                  @RequestParam(required = false) String name) {
        return R.success(employeeService.pageWithNameOrderByUpdateTime(page, pageSize, name));
    }
}
