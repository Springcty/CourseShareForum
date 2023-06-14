package com.software.blogcommon.entity;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Data
public class NewArticleBean implements Serializable{
    private String content;
    private Integer categoryId;
    private List<Integer> labelIds;
    private String title;
    private String descript;
    private Integer authorId;
}

