package com.zmbdp.springblog.model;

import com.zmbdp.springblog.common.Constants;
import lombok.Data;

@Data
public class Result<T> {
    private Integer code;// 200 - 成功;   -1 - 失败
    private String errorMsg;
    private T data;

    public static <T> Result <T> success(T data) {
        Result result = new Result<>();
        result.setCode(Constants.RESULT_SUCCESS);
        result.setErrorMsg("");
        result.setData(data);
        return result;
    }

    public static <T> Result <T> fail(String errorMsg) {
        Result result = new Result<>();
        result.setCode(Constants.RESULT_FAIL);
        result.setErrorMsg(errorMsg);
        return result;
    }

    public static <T> Result <T> fail(String errorMsg, T data) {
        Result result = new Result<>();
        result.setCode(Constants.RESULT_FAIL);
        result.setErrorMsg(errorMsg);
        result.setData(data);
        return result;
    }
}
