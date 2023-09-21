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
@Schema(name =  "AddressBook对象", description = "地址管理")
public class AddressBook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description =  "主键")
    private Long id;

    @Schema(description =  "用户id")
    private Long userId;

    @Schema(description =  "收货人")
    private String consignee;

    @Schema(description =  "性别 0 女 1 男")
    private Integer sex;

    @Schema(description =  "手机号")
    private String phone;

    @Schema(description =  "省级区划编号")
    private String provinceCode;

    @Schema(description =  "省级名称")
    private String provinceName;

    @Schema(description =  "市级区划编号")
    private String cityCode;

    @Schema(description =  "市级名称")
    private String cityName;

    @Schema(description =  "区级区划编号")
    private String districtCode;

    @Schema(description =  "区级名称")
    private String districtName;

    @Schema(description =  "详细地址")
    private String detail;

    @Schema(description =  "标签")
    private String label;

    @Schema(description = "默认 0 否 1是")
    private Boolean isDefault;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @Schema(description = "修改人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    @Schema(description = "是否删除")
    @TableLogic
    private Integer isDeleted;
}