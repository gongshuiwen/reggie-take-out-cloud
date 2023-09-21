package org.example.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;


@Getter
@Setter
@Schema(name =  "Employee对象", description = "员工信息")
public class Employee implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @Schema(description =  "主键")
    private Long id;

    @Schema(description =  "姓名")
    private String name;

    @Schema(description =  "用户名")
    private String username;

    @JsonIgnore
    @Schema(description =  "密码")
    private String password;

    @Schema(description =  "手机号")
    private String phone;

    @Schema(description =  "性别")
    private String sex;

    @Schema(description =  "身份证号")
    private String idNumber;

    @Schema(description =  "状态 0:禁用，1:正常")
    private Integer status;

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

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (getId() == 1) {
            return Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return status == 1;
    }
}
