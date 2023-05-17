package org.example.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.service.EmployeeService;
import org.example.reggie.common.R;
import org.example.reggie.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private static final String DEFAULT_PASSWORD = "123456";

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public R<Employee> get(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        if (employee == null) {
            return R.error("用户不存在！");
        }
        return R.success(employee);
    }

    @PostMapping
    public R<String> create(@RequestBody Employee employee) {
        employee.setPassword(DigestUtils.md5DigestAsHex(DEFAULT_PASSWORD.getBytes()));
        employeeService.save(employee);
        return R.success("");
    }

    @PutMapping
    public R<String> update(@RequestBody Employee employee) {
        employeeService.updateById(employee);
        return R.success("");
    }

    @DeleteMapping
    public R<String> delete(@RequestBody Employee employee) {
        employeeService.removeById(employee.getId());
        return R.success("");
    }

    @GetMapping("/page")
    public R<Page<Employee>> page(@RequestParam int page, @RequestParam int pageSize, @RequestParam @Nullable String name) {
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null, Employee::getName, name);
        wrapper.orderByDesc(Employee::getUpdateTime);
        return R.success(employeeService.page(new Page<>(page, pageSize), wrapper));
    }
}
