package org.example.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.reggie.entity.Employee;

public interface EmployeeService extends IService<Employee> {

    Page<Employee> pageWithNameOrderByUpdateTime(Long pageNum, Long pageSize, String name);

    Employee saveWithDefaultPassword(Employee employee);
}
