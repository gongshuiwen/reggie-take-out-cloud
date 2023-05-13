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

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.Objects;

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

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(wrapper);

        if (emp == null) return R.error("登录失败，用户不存在！");
        if (emp.getStatus() == 0) return R.error("登录失败，用户已被禁用！");
        if (!Objects.equals(emp.getPassword(), DigestUtils.md5DigestAsHex(employee.getPassword().getBytes())))
            return R.error("登录失败，用户密码错误！");

        request.getSession().setAttribute("employeeId", emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employeeId");
        return R.success("退出成功！");
    }
}
