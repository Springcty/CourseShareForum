package com.software.blogserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMenuDTO {

    private Integer id;

    private String name;

    private String path;

    private String component;

    private String icon;

    private Boolean hidden;
    //type=1表示普通用户,2表示管理员
    private Integer type;

    private List<UserMenuDTO> children;

}
