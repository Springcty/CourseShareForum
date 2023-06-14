package com.software.blogserver.mapper;

import com.software.blogserver.entity.Users;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.software.blogserver.vo.ArticlesVO;
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
public interface UsersDao extends BaseMapper<Users> {

    @Select("SELECT u.id, u.user_name, u.avatar, u.priviledged FROM users u "+
            "LIMIT #{start},#{pageSize}")
    List<Users> selectByPage(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

    @Select("SELECT u.id, u.user_name, u.avatar, u.priviledged "+
            "FROM users u, follow_items f WHERE u.id = f.followed_id AND f.followed_id = #{userId} "+
            "LIMIT #{start},#{pageSize}")
    List<Users> selectFans(@Param("start") Integer start, @Param("pageSize") Integer pageSize, @Param("userId") Integer userId);

    @Select("SELECT u.id, u.user_name, u.avatar, u.priviledged "+
            "FROM users u, follow_items f WHERE u.id = f.follower_id AND f.follower_id = #{userId} "+
            "LIMIT #{start},#{pageSize}")
    List<Users> selectFollowees(@Param("start") Integer start, @Param("pageSize") Integer pageSize, @Param("userId") Integer userId);

}
