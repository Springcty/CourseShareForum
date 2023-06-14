package com.software.blogserver.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
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
public class Blog_label implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField(value = "article_id")
    private Integer articleid;

    @TableField(value = "label_id")
    private Integer labelid;

}
