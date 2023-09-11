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
@Schema(name = "Dish对象", description = "菜品管理")
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "主键")
    private Long id;

    @Schema(name = "菜品名称")
    private String name;

    @Schema(name = "菜品分类id")
    private Long categoryId;

    @Schema(name = "菜品价格")
    private BigDecimal price;

    @Schema(name = "商品码")
    private String code;

    @Schema(name = "图片")
    private String image;

    @Schema(name = "描述信息")
    private String description;

    @Schema(name = "0 停售 1 起售")
    private Integer status;

    @Schema(name = "顺序")
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

    @TableField(exist = false)
    private String categoryName;

    @TableField(exist = false)
    private List<DishFlavor> flavors;

}
