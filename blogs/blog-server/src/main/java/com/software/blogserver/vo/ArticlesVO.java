package com.software.blogserver.vo;

import com.software.blogserver.entity.Articles;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ArticlesVO extends Articles implements Serializable {
    private String categoryName;
    private String username;
    private String avatar;
    private List<String> labelNameList;
}
