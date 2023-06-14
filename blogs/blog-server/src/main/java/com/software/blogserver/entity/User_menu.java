package com.software.blogserver.entity;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User_menu implements Serializable {
    private static final long serialVersionUID = 1L;

    //rid=1表示普通用户，2表示管理员
    private Integer rid;

    private Integer menuId;
}
