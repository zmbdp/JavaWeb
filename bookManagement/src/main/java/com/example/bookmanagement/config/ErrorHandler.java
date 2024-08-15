package com.example.bookmanagement.config;

import com.example.bookmanagement.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ResponseBody
@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    public Result exception(Exception e) {
        log.error("异常; e: {}", e);
        return Result.fail("内部错误");
    }

    @ExceptionHandler
    public Result nullPointerException(NullPointerException e) {
        log.error("异常; e: {}", e);
        return Result.fail("NullPointerException 异常");
    }

    @ExceptionHandler
    public Result arithmeticException(ArithmeticException e) {
        log.error("异常; e: {}", e);
        return Result.fail("ArithmeticException 异常");
    }
}
