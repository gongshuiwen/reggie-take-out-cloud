package org.example.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.BaseContext;
import org.example.reggie.common.R;
import org.example.reggie.entity.Orders;
import org.example.reggie.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/userPage")
    public R<Page<Orders>> userPage(Long page, Long pageSize) {
        return R.success(ordersService.pageWithUserIdOrderByOrderTimeDesc(page, pageSize, BaseContext.getCurrentUserId()));
    }

    @GetMapping("/page")
    public R<Page<Orders>> page(Long page, Long pageSize) {
        return R.success(ordersService.pageWithUserIdOrderByOrderTimeDesc(page, pageSize, null));
    }

    @PostMapping("/submit")
    public R<Boolean> submit(@RequestBody Orders order) {
        order.setUserId(BaseContext.getCurrentUserId());
        return R.success(ordersService.submitOrder(order));
    }
}
