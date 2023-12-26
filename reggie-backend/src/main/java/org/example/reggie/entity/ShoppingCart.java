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

    @Schema(description =  "主键")
    private Long id;

    @Schema(description =  "名称")
    private String name;

    @Schema(description =  "图片")
    private String image;

    @Schema(description =  "主键")
    private Long userId;

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

    @Schema(description =  "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

}
