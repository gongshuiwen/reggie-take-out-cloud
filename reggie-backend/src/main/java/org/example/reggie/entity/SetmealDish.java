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

    @Schema(description =  "主键")
    private Long id;

    @Schema(description =  "套餐id")
    private Long setmealId;

    @Schema(description =  "菜品id")
    private Long dishId;

    @Schema(description =  "菜品名称 （冗余字段）")
    private String name;

    @Schema(description =  "菜品原价（冗余字段）")
    private BigDecimal price;

    @Schema(description =  "份数")
    private Integer copies;

    @Schema(description =  "排序")
    private Integer sort;

    @Schema(description =  "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description =  "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(description =  "创建人")
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @Schema(description =  "修改人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    @Schema(description =  "是否删除")
    @TableLogic
    private Integer isDeleted;

}
