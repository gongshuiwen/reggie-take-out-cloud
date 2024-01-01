package org.example.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.reggie.user.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
