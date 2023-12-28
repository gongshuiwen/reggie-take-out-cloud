package org.example.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.BaseContext;
import org.example.reggie.common.protocal.R;
import org.example.reggie.entity.Orders;
import org.example.reggie.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "订单接口")
@Slf4j
@RestController
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @Operation(summary = "分页查询用户订单")
    @GetMapping("/userPage")
    public R<Page<Orders>> userPage(Long page, Long pageSize) {
        return R.success(ordersService.pageWithUserIdOrderByOrderTimeDesc(page, pageSize, BaseContext.getCurrentUserId()));
    }

    @Operation(summary = "分页查询所有订单")
    @GetMapping("/page")
    public R<Page<Orders>> page(Long page, Long pageSize) {
        return R.success(ordersService.pageWithUserIdOrderByOrderTimeDesc(page, pageSize, null));
    }

    @Operation(summary = "提交订单")
    @PostMapping("/submit")
    public R<Boolean> submit(@RequestBody Orders order) {
        order.setUserId(BaseContext.getCurrentUserId());
        return R.success(ordersService.submitOrder(order));
    }
}
