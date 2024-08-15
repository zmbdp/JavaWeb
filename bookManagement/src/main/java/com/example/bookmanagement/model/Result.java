package com.example.bookmanagement.model;

import com.example.bookmanagement.enums.ResultCode;
import lombok.Data;

@Data
public class Result<T> {
    /**
     * 业务状态码
     */
    private ResultCode code;  //0-成功  -1 失败  -2 未登录
    /**
     * 错误信息
     */
    private String errMsg;
    /**
     * 返回的数据
     */
    private T data;

    // 成功
    public static <T> Result<T> success(T data) {
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS);
        result.setErrMsg("");
        result.setData(data);
        return result;
    }

    // 失败
    public static <T> Result<T> fail(String errMsg) {
        Result result = new Result();
        result.setCode(ResultCode.FAIL);
        result.setErrMsg(errMsg);
        result.setData(null);
        return result;
    }

    public static <T> Result<T> fail(String errMsg, T data) {
        Result result = new Result();
        result.setCode(ResultCode.FAIL);
        result.setErrMsg(errMsg);
        result.setData(data);
        return result;
    }

    // 未登录
    public static <T> Result<T> unlogin() {
        Result result = new Result();
        result.setCode(ResultCode.UNLOGIN);
        result.setErrMsg("未登录!!!");
        result.setData(null);
        return result;
    }
}
