package com.software.blogserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software.blogcommon.entity.QueryPageBean;
import com.software.blogserver.entity.Blog_label;
import com.software.blogserver.entity.Follow_items;
import com.software.blogserver.entity.Users;
import com.software.blogserver.file.FileOperation;
import com.software.blogserver.mapper.Follow_itemsDao;
import com.software.blogserver.mapper.UsersDao;
import com.software.blogserver.service.IUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software.blogserver.vo.ArticlesVO;
import com.software.blogserver.vo.CommentAdminVO;
import com.software.blogserver.vo.UserInfoVO;
import com.software.blogserver.vo.UsersVO;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.software.blogcommon.entity.NewAvatarBean;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yzf
 * @since 2023-05-15
 */
@Service
public class UsersServiceImpl extends ServiceImpl<UsersDao, Users> implements IUsersService {

    @Autowired
    private UsersDao usersDao;
    @Autowired
    private Follow_itemsDao follow_itemsDao;
    @Autowired
    private FileOperation fileOperation;
    @Override
    public Users getByUserPwd(String username, String pwd) {
        QueryWrapper<Users> qw = new QueryWrapper<>();
        qw.eq("user_name", username);
        qw.eq("pass_word", pwd);
        qw.select("id", "user_name");
        qw.last("limit 1");
        return usersDao.selectOne(qw);
    }

    @Override
    public Users getByUser(String username) {
        QueryWrapper<Users> qw = new QueryWrapper<>();
        qw.eq("user_name", username);
        return usersDao.selectOne(qw);
    }

    @Override
    public boolean existUser(String userName) {
        QueryWrapper<Users> qw = new QueryWrapper<>();
        qw.eq("user_name", userName);
        return usersDao.selectCount(qw) > 0;
    }

    @Override
    public boolean updateUser(UsersVO users) {
        QueryWrapper<Users> qw = new QueryWrapper<>();
        qw.eq("id", users.getId());
        Users user = usersDao.selectOne(qw);
        if (!Objects.equals(user.getUsername(), users.getUsername())){
            QueryWrapper<Users> qw2 = new QueryWrapper<>();
            qw2.eq("user_name", users.getUsername());
            if (usersDao.selectCount(qw2) > 0)
                return false;
        }
        user.setUsername(users.getUsername());
        user.setPriviledged(users.getPriviledged());
        user.setAvatar(users.getAvatar());
        user.setPassword(users.getPassword());
        usersDao.updateById(user);
        return true;
    }

    @Override
    public String setAvatar(NewAvatarBean newAvatarBean, HttpServletRequest request) throws IOException {
        //获取当前时间和随机数，组合生成文件名
        LocalDateTime time = LocalDateTime.now();
        Integer randomId = (int) (Math.random() * 1024);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String originalName = newAvatarBean.getContent().getOriginalFilename();
        assert originalName != null;
        String suffix = originalName.substring(originalName.indexOf('.')); //获取后缀
        String fileName = time.format(fmt) + randomId + suffix;

        //上传文件
        MultipartFile file = newAvatarBean.getContent();
        String path = request.getServletContext().getRealPath("/avatars/");
        fileOperation.saveFile(file, path, fileName);

        return path + fileName;
    }

    @Override
    public String getAvatar(Integer userId, HttpServletRequest request) {
        //获取avatars目录路径
        String path = request.getServletContext().getRealPath("/avatars/");
        //获取用户id对应头像文件名
        QueryWrapper<Users> qw = new QueryWrapper<>();
        qw.eq("id", userId);
        qw.select("avatar");
        String fileName = usersDao.selectOne(qw).getAvatar();
        return path + fileName;
    }

    @Override
    public Page<Users> getAllUser(QueryPageBean queryPageBean) {
        Page<Users> page = new Page<>();
        page.setTotal(usersDao.selectCount(null));
        Integer start = queryPageBean.getPageSize() * (queryPageBean.getCurrentPage() - 1);
        page.setRecords(usersDao.selectByPage(start, queryPageBean.getPageSize()));
        return page;
    }

    @Override
    public Page<Users> getFans(QueryPageBean queryPageBean, HttpServletRequest request){
        Page<Users> page = new Page<>(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        QueryWrapper<Follow_items> wrapper = new QueryWrapper<>();
        Users user = (Users) request.getAttribute("currentUser");
        wrapper.eq("followed_id", user.getId());
        page.setTotal(follow_itemsDao.selectCount(wrapper));
        Integer start = queryPageBean.getPageSize() * (queryPageBean.getCurrentPage() - 1);
        page.setRecords(usersDao.selectFans(start, queryPageBean.getPageSize(), user.getId()));
        return page;
    }

    @Override
    public Page<Users> getFollowees(QueryPageBean queryPageBean, HttpServletRequest request){
        Page<Users> page = new Page<>(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        QueryWrapper<Follow_items> wrapper = new QueryWrapper<>();
        Users user = (Users) request.getAttribute("currentUser");
        wrapper.eq("follower_id", user.getId());
        page.setTotal(follow_itemsDao.selectCount(wrapper));
        Integer start = queryPageBean.getPageSize() * (queryPageBean.getCurrentPage() - 1);
        page.setRecords(usersDao.selectFollowees(start, queryPageBean.getPageSize(), user.getId()));
        return page;
    }

    @Override
    public void updatePrivilege(Integer id, Integer type) {
        Users users = usersDao.selectById(id);
        boolean privilege = (type == 1);
        users.setPriviledged(privilege);
        usersDao.updateById(users);
    }

    @Override
    public UserInfoVO getUserInfo(Integer userId, Users user) {
        Users users = usersDao.selectById(userId);
        UserInfoVO userInfo = new UserInfoVO();
        //1、username, avatar
        userInfo.setUsername(users.getUsername());
        userInfo.setAvatar(users.getAvatar());
        //2、是否关注
        Follow_items follow_items = follow_itemsDao.selectOne(
                new QueryWrapper<Follow_items>().eq("followed_id", userId).eq("follower_id", user.getId()));
        userInfo.setFollowed(follow_items != null);
        //3、关注数和粉丝数
        QueryWrapper<Follow_items> qw = new QueryWrapper<>();
        qw.eq("follower_id", userId);
        userInfo.setFollows(Math.toIntExact(follow_itemsDao.selectCount(qw)));
        QueryWrapper<Follow_items> qw2 = new QueryWrapper<>();
        qw2.eq("followed_id", userId);
        userInfo.setFans(Math.toIntExact(follow_itemsDao.selectCount(qw2)));
        return userInfo;
    }
}
