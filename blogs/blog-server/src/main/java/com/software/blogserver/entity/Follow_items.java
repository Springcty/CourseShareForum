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
public class Follow_items implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "followed_id")
    private Integer followedid;

    @TableField(value = "follower_id")
    private Integer followerid;


}
