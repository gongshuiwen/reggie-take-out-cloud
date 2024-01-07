package org.example.reggie.user.context;

import org.example.reggie.user.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserInfoContext {

    public static Long get() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return ((User) principal).getId();
        }
        return null;
    }
}
