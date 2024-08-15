package com.zmbdp.springblog.config;

import com.zmbdp.springblog.common.Constants;
import com.zmbdp.springblog.model.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@ControllerAdvice
public class ErrorAdvice {
    @ExceptionHandler
    public Result errorHandler(Exception e) {
        Result result = new Result<>();
        result.setCode(Constants.RESULT_FAIL);
        result.setErrorMsg("内部错误，请联系管理员");
        return result;
    }
}
