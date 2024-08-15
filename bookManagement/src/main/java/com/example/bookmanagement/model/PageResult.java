package com.example.bookmanagement.model;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    /**
     * 当前页的数据
     */
    private List<T> records;// 应用泛型，能让更多地方使用
    /**
     * 一共有几条
     */
    private Integer total;
    // 直接全部发给前端，然后让前端判定当前是第几页
    private PageRequest pageRequest;
}
