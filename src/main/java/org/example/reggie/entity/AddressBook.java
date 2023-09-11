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
@TableName("address_book")
@Schema(name = "AddressBook对象", description = "地址管理")
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(name = "主键")
    private Long id;

    @Schema(name = "用户id")
    private Long userId;

    @Schema(name = "收货人")
    private String consignee;

    @Schema(name = "性别 0 女 1 男")
    private Integer sex;

    @Schema(name = "手机号")
    private String phone;

    @Schema(name = "省级区划编号")
    private String provinceCode;

    @Schema(name = "省级名称")
    private String provinceName;

    @Schema(name = "市级区划编号")
    private String cityCode;

    @Schema(name = "市级名称")
    private String cityName;

    @Schema(name = "区级区划编号")
    private String districtCode;

    @Schema(name = "区级名称")
    private String districtName;

    @Schema(name = "详细地址")
    private String detail;

    @Schema(name = "标签")
    private String label;

    @Schema(name = "默认 0 否 1是")
    private Boolean isDefault;

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