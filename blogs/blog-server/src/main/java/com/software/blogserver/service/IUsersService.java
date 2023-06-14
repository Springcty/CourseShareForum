package com.software.blogserver.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software.blogcommon.entity.NewAvatarBean;
import com.software.blogcommon.entity.QueryPageBean;
import com.software.blogserver.entity.Users;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software.blogserver.vo.ArticlesVO;
import com.software.blogserver.vo.UserInfoVO;
import com.software.blogserver.vo.UsersVO;
import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yzf
 * @since 2023-05-15
 */
public interface IUsersService extends IService<Users> {

    Users getByUserPwd(String username, String pwd);

    Users getByUser(String username);

    boolean existUser(String userName);

    boolean updateUser(UsersVO users);

    String setAvatar(NewAvatarBean newAvatarBean, HttpServletRequest request) throws IOException;

    String getAvatar(Integer userId, HttpServletRequest request);

    Page<Users> getAllUser(QueryPageBean queryPageBean);

    public Page<Users> getFans(QueryPageBean queryPageBean, HttpServletRequest request);

    public Page<Users> getFollowees(QueryPageBean queryPageBean, HttpServletRequest request);

    public void updatePrivilege(Integer id, Integer type);

    UserInfoVO getUserInfo(Integer userId, Users user);
}
