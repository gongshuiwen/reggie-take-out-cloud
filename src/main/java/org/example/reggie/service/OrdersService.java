package org.example.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.reggie.entity.Orders;
import org.springframework.transaction.annotation.Transactional;

public interface OrdersService extends IService<Orders> {

    @Transactional
    public void submitOrder(Orders order, Long userId);
}
