package org.example.reggie.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;


@Getter
@Setter
@TableName("order_detail")
@Schema(name = "OrderDetail对象", description = "订单明细表")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "主键")
    private Long id;

    @Schema(name = "名字")
    private String name;

    @Schema(name = "图片")
    private String image;

    @Schema(name = "订单id")
    private Long orderId;

    @Schema(name = "菜品id")
    private Long dishId;

    @Schema(name = "套餐id")
    private Long setmealId;

    @Schema(name = "口味")
    private String dishFlavor;

    @Schema(name = "数量")
    private Integer number;

    @Schema(name = "金额")
    private BigDecimal amount;

}
