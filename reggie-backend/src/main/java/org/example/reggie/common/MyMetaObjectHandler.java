package org.example.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());

        if (BaseContext.getCurrentEmployeeId() != null) {
            metaObject.setValue("createUser", BaseContext.getCurrentEmployeeId());
            metaObject.setValue("updateUser", BaseContext.getCurrentEmployeeId());
        } else {
            metaObject.setValue("createUser", BaseContext.getCurrentUserId());
            metaObject.setValue("updateUser", BaseContext.getCurrentUserId());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());

        if (BaseContext.getCurrentEmployeeId() != null) {
            metaObject.setValue("updateUser", BaseContext.getCurrentEmployeeId());
        } else {
            metaObject.setValue("updateUser", BaseContext.getCurrentUserId());
        }
    }
}
