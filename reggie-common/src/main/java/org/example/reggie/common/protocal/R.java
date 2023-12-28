package org.example.reggie.common.protocal;

import lombok.Data;

@Data
public class R<T> {

    private Integer code;
    private String msg;
    private T data;

    public static <T> R<T> success(T obj) {
        R<T> r = new R<T>();
        r.setCode(1);
        r.setMsg("");
        r.setData(obj);
        return r;
    }

    public static <T> R<T> error(String message) {
        R<T> r = new R<T>();
        r.setCode(0);
        r.setMsg(message);
        return r;
    }
}
