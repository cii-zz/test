package com.userlabel.core.common;

import lombok.Data;

/**
 * 分页请求对象
 */
@Data
public class PageRequest {
    
    /**
     * 当前页码
     */
    private Integer pageNum = 1;

    /**
     * 每页数量
     */
    private Integer pageSize = 10;

    /**
     * 排序字段
     */
    private String orderBy;

    /**
     * 排序方式：asc/desc
     */
    private String orderType;
}