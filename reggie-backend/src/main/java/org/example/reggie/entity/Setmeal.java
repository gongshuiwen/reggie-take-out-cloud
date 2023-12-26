package org.example.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Schema(name = "Setmeal对象", description = "套餐")
public class Setmeal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description =  "主键")
    private Long id;

    @Schema(description =  "菜品分类id")
    private Long categoryId;

    @Schema(description =  "套餐名称")
    private String name;

    @Schema(description =  "套餐价格")
    private BigDecimal price;

    @Schema(description =  "状态 0:停用 1:启用")
    private Integer status;

    @Schema(description =  "编码")
    private String code;

    @Schema(description =  "描述信息")
    private String description;

    @Schema(description =  "图片")
    private String image;

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

    @TableField(exist = false)
    private String categoryName;

    @TableField(exist = false)
    private List<SetmealDish> setmealDishes;

}
