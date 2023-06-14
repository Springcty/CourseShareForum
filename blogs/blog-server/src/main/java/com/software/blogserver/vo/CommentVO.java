package com.software.blogserver.vo;

import com.software.blogserver.entity.Comment_items;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommentVO extends Comment_items implements Serializable {
    private String username;
    private String avatar;
}
