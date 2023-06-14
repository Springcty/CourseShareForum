package com.software.blogserver.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class ArticlesShowVO extends ArticlesVO implements Serializable {
    private boolean isLiked;
    private boolean isStared;
    private boolean isFollowed;
}
