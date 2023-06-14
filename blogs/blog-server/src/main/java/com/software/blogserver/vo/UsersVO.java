package com.software.blogserver.vo;

import com.software.blogserver.entity.Users;
import lombok.Data;

import java.io.Serializable;

@Data
public class UsersVO extends Users implements Serializable {
    private String old_password;
}
