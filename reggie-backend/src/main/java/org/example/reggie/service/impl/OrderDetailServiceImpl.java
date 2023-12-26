package org.example.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.reggie.entity.OrderDetail;
import org.example.reggie.mapper.OrderDetailsMapper;
import org.example.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailsMapper, OrderDetail> implements OrderDetailService {
}
