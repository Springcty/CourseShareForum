package com.software.blogserver.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfoVO implements Serializable {
    private String username;
    private String avatar;
    private boolean isFollowed;
    private Integer fans;
    private Integer follows;
}
