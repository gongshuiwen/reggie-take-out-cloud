package org.example.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@TableName("setmeal_dish")
@Schema(name = "SetmealDish对象", description = "套餐菜品关系")
public class SetmealDish implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "主键")
    private Long id;

    @Schema(name = "套餐id")
    private Long setmealId;

    @Schema(name = "菜品id")
    private Long dishId;

    @Schema(name = "菜品名称 （冗余字段）")
    private String name;

    @Schema(name = "菜品原价（冗余字段）")
    private BigDecimal price;

    @Schema(name = "份数")
    private Integer copies;

    @Schema(name = "排序")
    private Integer sort;

    @Schema(name = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(name = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(name = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @Schema(name = "修改人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    @Schema(name = "是否删除")
    @TableLogic
    private Integer isDeleted;

}
