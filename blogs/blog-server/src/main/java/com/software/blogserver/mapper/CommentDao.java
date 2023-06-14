package com.software.blogserver.mapper;

import com.software.blogserver.entity.Comment_items;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.software.blogserver.vo.CommentAdminVO;
import com.software.blogserver.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yzf
 * @since 2023-05-15
 */
@Mapper
public interface CommentDao extends BaseMapper<Comment_items> {
    @Select("SELECT c.id, c.article_id, c.user_id, c.time, c.content, u.user_name, u.avatar "+
            "FROM comment_items c, users u WHERE c.user_id = u.id AND c.article_id = #{blogId}")
    public List<CommentVO> getByBlogid(@Param("blogId") Integer blogId);

    @Select("SELECT c.id, c.article_id, c.user_id, c.time, c.content, u.user_name, u.avatar, a.title "+
            "FROM comment_items c, users u, articles a WHERE c.user_id = u.id AND c.article_id = a.id "+
            "ORDER BY c.time DESC LIMIT #{start},#{pageSize}")
    List<CommentAdminVO> selectByPage(@Param("start") Integer start, @Param("pageSize") Integer pageSize);
}
