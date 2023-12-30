package org.example.reggie.common.context;

public class UserInfoContext {

    private static final ThreadLocal<Long> userThreadLocal = new ThreadLocal<>();

    public static void set(Long userId) {
        userThreadLocal.set(userId);
    }

    public static Long get() {
        return userThreadLocal.get();
    }

    public static void remove() {
        userThreadLocal.remove();
    }
}

