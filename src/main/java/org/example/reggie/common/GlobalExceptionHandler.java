package org.example.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.example.reggie.exception.MyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
@Slf4j
@ResponseBody
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception) {
        return R.error(exception.getMessage());
    }

    @ExceptionHandler(MyException.class)
    public R<String> exceptionHandler(MyException exception) {
        return R.error(exception.getMessage());
    }
}
