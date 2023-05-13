package org.example.reggie.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R<T> {

    private Integer code;
    private String msg;
    private T data;
    private Map<String, String> map = new HashMap<>();

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

    public R<T> add(String key, String value) {
        this.map.put(key, value);
        return this;
    }
}
