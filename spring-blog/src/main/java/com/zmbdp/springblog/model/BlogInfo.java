package com.zmbdp.springblog.model;

import com.zmbdp.springblog.utils.DateUtils;
import lombok.Data;

import java.util.Date;

@Data
public class BlogInfo {
    private Integer id;
    // 标题
    private String title;
    // 博客内容
    private String content;
    private Integer userId;
    private Integer deleteFlag;
    private Date createTime;
    private Date updateTime;
    private boolean isLoginUser;

    public String getUpdateTime() {
        return DateUtils.formatDate(updateTime);
    }
}
