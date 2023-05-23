package org.example.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.reggie.entity.Employee;
import org.example.reggie.mapper.EmployeeMapper;
import org.example.reggie.service.EmployeeService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService, UserDetailsService {

    private static final String DEFAULT_PASSWORD = "123456";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername, username);
        return this.getOne(wrapper);
    }

    @Override
    public Page<Employee> pageWithNameOrderByUpdateTime(Long pageNum, Long pageSize ,String name) {
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null, Employee::getName, name);
        wrapper.orderByDesc(Employee::getUpdateTime);
        return this.page(new Page<>(pageNum, pageSize), wrapper);
    }

    @Override
    public Employee saveWithDefaultPassword(Employee employee) {
        employee.setPassword(DigestUtils.md5DigestAsHex(DEFAULT_PASSWORD.getBytes()));
        this.save(employee);
        return employee;
    }
}
