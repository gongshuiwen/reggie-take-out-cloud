package org.example.reggie.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.reggie.user.entity.User;

public interface UserService extends IService<User> {

    User selectByPhone(String phone);

    User registerByPhone(String phone);
}
