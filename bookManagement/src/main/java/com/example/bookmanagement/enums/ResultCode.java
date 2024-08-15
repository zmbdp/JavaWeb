package com.example.bookmanagement.enums;

import lombok.Data;

public enum ResultCode {
    SUCCESS(0),
    FAIL(-1),
    UNLOGIN(-2)
    ;
    // 设置状态码
    //0-成功  -1 失败  -2 未登录
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    ResultCode(Integer code) {
        this.code = code;
    }
}
