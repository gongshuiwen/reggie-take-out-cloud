package org.example.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@TableName("shopping_cart")
@Schema(name = "ShoppingCart对象", description = "购物车")
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "主键")
    private Long id;

    @Schema(name = "名称")
    private String name;

    @Schema(name = "图片")
    private String image;

    @Schema(name = "主键")
    private Long userId;

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

    @Schema(name = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
