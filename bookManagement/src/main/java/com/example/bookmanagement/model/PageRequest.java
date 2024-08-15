package com.example.bookmanagement.model;

import lombok.Data;

@Data
public class PageRequest {
    // 当前页码
    private Integer currentPage = 1;
    // 一页几个
    private Integer pageSize = 10;
}
