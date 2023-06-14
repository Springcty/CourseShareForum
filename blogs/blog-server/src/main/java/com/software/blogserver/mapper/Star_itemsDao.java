package com.software.blogserver.mapper;

import com.software.blogserver.entity.Star_items;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.software.blogserver.vo.ArticlesVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

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
@Repository
public interface Star_itemsDao extends BaseMapper<Star_items> {

    @Select("SELECT a.id, a.verified, a.selected, a.time, a.category_id, a.title, a.content, a.descript, a.likes, a.stars, a.views, a.comments, a.author_id, c.category_name, u.user_name, u.avatar " +
            "FROM articles a, users u, categories c, star_items s " +
            "WHERE a.category_id = c.id AND a.author_id = u.id AND s.article_id = a.id AND s.user_id = #{id} "+
            "ORDER BY a.time DESC LIMIT #{start}, #{pageSize}")
    List<ArticlesVO> selectByUser(@Param("id") Integer id, @Param("start")Integer start, @Param("pageSize")Integer pageSize);
}
