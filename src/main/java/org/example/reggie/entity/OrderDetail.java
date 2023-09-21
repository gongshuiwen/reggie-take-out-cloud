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

    @Schema(description =  "主键")
    private Long id;

    @Schema(description =  "名字")
    private String name;

    @Schema(description =  "图片")
    private String image;

    @Schema(description =  "订单id")
    private Long orderId;

    @Schema(description =  "菜品id")
    private Long dishId;

    @Schema(description =  "套餐id")
    private Long setmealId;

    @Schema(description =  "口味")
    private String dishFlavor;

    @Schema(description =  "数量")
    private Integer number;

    @Schema(description =  "金额")
    private BigDecimal amount;

}
