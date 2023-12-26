package org.example.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@TableName("dish_flavor")
@Schema(name = "DishFlavor对象", description = "菜品口味关系表")
public class DishFlavor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description =  "主键")
    private Long id;

    @Schema(description =  "菜品")
    private Long dishId;

    @Schema(description =  "口味名称")
    private String name;

    @Schema(description =  "口味数据list")
    private String value;

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
