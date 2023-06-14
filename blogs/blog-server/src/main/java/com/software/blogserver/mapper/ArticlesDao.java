package com.software.blogserver.mapper;

import com.software.blogcommon.entity.QueryPageBean;
import com.software.blogserver.dto.BlogStatisticsDTO;
import com.software.blogserver.entity.Articles;
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
@Repository
@Mapper
public interface ArticlesDao extends BaseMapper<Articles> {

    List<ArticlesVO> selectPageNew(@Param("queryPageBean") QueryPageBean queryPageBean);
    List<ArticlesVO> selectPageHot(@Param("queryPageBean") QueryPageBean queryPageBean);
    List<ArticlesVO> selectByLabel(@Param("queryPageBean") QueryPageBean queryPageBean);
    List<ArticlesVO> selectByCategory(@Param("queryPageBean") QueryPageBean queryPageBean);
    @Select("SELECT a.id, c.category_name, u.user_name, u.avatar, a.verified, a.selected, a.time, a.category_id, a.title, a.content, a.descript, a.likes, a.stars, a.views, a.comments, a.author_id " +
            "FROM articles a, users u, categories c WHERE a.category_id = c.id AND a.author_id = u.id AND a.verified = true "+
            "ORDER BY a.time DESC")
    List<ArticlesVO> selectLatest();
    List<ArticlesVO> selectSearch(@Param("queryPageBean") QueryPageBean queryPageBean);
    @Select("SELECT a.id, a.verified, a.selected, a.time, a.category_id, a.title, a.content, a.descript, a.likes, a.stars, a.views, a.comments, a.author_id, c.category_name, u.user_name, u.avatar " +
            "FROM articles a, users u, categories c "+
            "WHERE a.category_id = c.id AND a.author_id = u.id AND a.author_id = #{id} " +
            "ORDER BY a.time DESC LIMIT #{start},#{pageSize}")
    List<ArticlesVO> selectByUser(@Param("id") Integer id, @Param("start")Integer start, @Param("pageSize")Integer pageSize);

    @Select("SELECT SUM(views) FROM articles;")
    Integer getViews();

    @Select("SELECT DATE_FORMAT( time, \"%Y-%m-%d\" ) AS date, COUNT( 1 ) AS count\n" +
            "FROM articles " +
            "GROUP BY date\n" +
            "ORDER BY date DESC")
    List<BlogStatisticsDTO> listArticleStatistics();

    List<ArticlesVO> selectPageAdmin(@Param("queryPageBean") QueryPageBean queryPageBean);

    Integer selectCountByLabel(@Param("queryPageBean") QueryPageBean queryPageBean);
}
