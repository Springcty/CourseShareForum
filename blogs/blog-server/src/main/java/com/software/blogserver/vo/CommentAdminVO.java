package com.software.blogserver.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentAdminVO extends CommentVO implements Serializable {
    private String title;
}
