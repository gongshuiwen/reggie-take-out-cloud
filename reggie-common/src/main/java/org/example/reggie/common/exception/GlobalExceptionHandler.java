package org.example.reggie.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.reggie.common.protocal.R;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */
@Slf4j
@ResponseBody
@RestControllerAdvice
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
