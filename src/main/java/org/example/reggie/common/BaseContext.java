package org.example.reggie.common;

public class BaseContext {

    private static final ThreadLocal<Long> currentEmployeeId = new ThreadLocal<>();
    private static final ThreadLocal<Long> currentUserId = new ThreadLocal<>();

    public static Long getCurrentEmployeeId() {
        return currentEmployeeId.get();
    }

    public static void setCurrentEmployeeId(Long currentId) {
        currentEmployeeId.set(currentId);
    }

    public static Long getCurrentUserId() {
        return currentUserId.get();
    }

    public static void setCurrentUserId(Long currentId) {
        currentUserId.set(currentId);
    }
}
