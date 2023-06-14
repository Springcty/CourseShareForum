package com.software.blogserver.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author yzf
 * @since 2023-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Integer id;

    @TableField(value = "user_name")
    private String username;

    @TableField(value = "pass_word")
    private String password;

    private String avatar;

    private Boolean priviledged;

}
