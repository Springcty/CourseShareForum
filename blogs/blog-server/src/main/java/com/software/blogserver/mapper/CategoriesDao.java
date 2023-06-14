package com.software.blogserver.mapper;

import com.software.blogserver.entity.Categories;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.software.blogserver.vo.CategoriesVO;
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
public interface CategoriesDao extends BaseMapper<Categories> {
//    @Select("SELECT DISTINCT c.id, c.content, COUNT(a.category_id) category_count " +
//            "FROM categories c LEFT OUTER JOIN articles a ON c.id = a.category_id " +
//            "GROUP BY c.id ORDER BY COUNT(a.category_id) DESC")
    List<CategoriesVO> getCategoryCount();
    @Select("SELECT COUNT(a.id) category_count\n" +
            "FROM categories c LEFT OUTER JOIN articles a ON c.id = a.category_id\n" +
            "GROUP BY c.id\n" +
            "ORDER BY COUNT(c.id) DESC")
    Integer getVerified(@Param("id") Integer id);
}
