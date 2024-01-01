package org.example.reggie.common;

import org.example.reggie.entity.Employee;
import org.example.reggie.user.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseContext {

    public static Long getCurrentEmployeeId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Employee) {
            return ((Employee) principal).getId();
        }
        return null;
    }

    @Deprecated
    @SuppressWarnings("all")
    public static void setCurrentEmployeeId(Long currentId) {
    }

    public static Long getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return ((User) principal).getId();
        }
        return null;
    }

    @Deprecated
    @SuppressWarnings("all")
    public static void setCurrentUserId(Long currentId) {
    }
}
