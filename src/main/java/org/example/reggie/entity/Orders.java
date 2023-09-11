package org.example.reggie.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@Schema(name = "Orders对象", description = "订单表")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "主键")
    private Long id;

    @Schema(name = "订单号")
    private String number;

    @Schema(name = "订单状态 1待付款，2待派送，3已派送，4已完成，5已取消")
    private Integer status;

    @Schema(name = "下单用户")
    private Long userId;

    @Schema(name = "地址id")
    private Long addressBookId;

    @Schema(name = "下单时间")
    private LocalDateTime orderTime;

    @Schema(name = "结账时间")
    private LocalDateTime checkoutTime;

    @Schema(name = "支付方式 1微信,2支付宝")
    private Integer payMethod;

    @Schema(name = "实收金额")
    private BigDecimal amount;

    @Schema(name = "备注")
    private String remark;

    private String phone;

    private String address;

    private String userName;

    private String consignee;

}
