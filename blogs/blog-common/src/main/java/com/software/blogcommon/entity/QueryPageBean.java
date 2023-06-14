package com.software.blogcommon.entity;

import lombok.Data;

import java.io.Serializable;

/*
用于封装查询条件
 */
@Data
public class QueryPageBean implements Serializable {
    private Integer currentPage;    //页码
    private Integer pageSize;       //每页记录数
    private String queryString;     //查询条件
    private String sort;            //排序方式(new / hot)

    private Integer categoryId;     //分类id
    private Integer labelId;        //标签id
}
