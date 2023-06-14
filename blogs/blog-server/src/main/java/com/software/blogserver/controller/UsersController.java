package com.software.blogserver.controller;


import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.software.blogcommon.entity.LoginResult;
import com.software.blogcommon.entity.NewAvatarBean;
import com.software.blogcommon.entity.QueryPageBean;
import com.software.blogcommon.entity.Result;
import com.software.blogcommon.enums.CodeEnum;
import com.software.blogserver.annotation.LoginRequired;
import com.software.blogserver.entity.Users;
import com.software.blogserver.service.IArticlesService;
import com.software.blogserver.service.IUsersService;
import com.software.blogserver.utils.JWTUtils;
import com.software.blogserver.utils.RedisUtils;
import com.software.blogserver.vo.UsersVO;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.software.blogcommon.constant.RedisConst.HOUR;
import static com.software.blogcommon.constant.RedisConst.TOKEN_ALLOW_LIST;
import static java.lang.Math.abs;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yzf
 * @since 2023-05-15
 */
@RestController
@RequestMapping("/users")
// @CrossOrigin(origins = {"*"})
public class UsersController {

    private static final String slat = "seblog!#$";
    @Autowired
    private IUsersService iUsersService;

    @Autowired
    private IArticlesService iArticlesService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisUtils redisUtils;

    @PostMapping("/login")
    public LoginResult login(@RequestBody Users user){
        LoginResult lr = userLogin(user, false);
        return lr;
    }

    @PostMapping("/loginAdmin")
    public LoginResult loginAdmin(@RequestBody Users user){
        LoginResult lr = userLogin(user, true);
        return lr;
    }

    public LoginResult userLogin(Users user, boolean admin){
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return LoginResult.fail(CodeEnum.VALID_ERROR);
        }
        String pwd = DigestUtils.md5Hex(password + slat);
        Users users = iUsersService.getByUser(username);
        if (users == null){
            return LoginResult.fail(CodeEnum.USERNAME_NOT_EXIST);
        }
        if (!users.getPriviledged() && admin){
            return LoginResult.fail(CodeEnum.PERMISSION_DENIED);
        }
        users = iUsersService.getByUserPwd(username, pwd);
        if (users == null){
            return LoginResult.fail(CodeEnum.PASSWORD_WRONG);
        }
        HashMap<String, String> payload = new HashMap<>();
        payload.put("id", String.valueOf(users.getId()));
        payload.put("username", users.getUsername());
        String token = JWTUtils.getToken(payload);
        redisUtils.set(TOKEN_ALLOW_LIST + users.getId(), token, HOUR);
        return LoginResult.ok("token生成成功", token, users);
    }

    @GetMapping("/logout")
    public Result logout(@RequestHeader("Authorization") String token){
        return Result.ok("用户退出成功");
    }

    @PostMapping("/register")
    public Result register(@RequestBody Users user){
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return Result.fail(CodeEnum.VALID_ERROR);
        }
        if (iUsersService.existUser(username))
            return Result.fail(CodeEnum.USERNAME_EXIST);
        Integer uid = (int) IdWorker.getId(Users.class);
        user.setId(abs(uid));
        user.setPassword(DigestUtils.md5Hex(password + slat));
        user.setPriviledged(false);
        if (iUsersService.save(user))
            return Result.ok("用户注册成功", user);
        return Result.fail(CodeEnum.SYSTEM_ERROR);
    }

    @PostMapping(value = "/updateUser")
    public Result updateUser(@RequestBody UsersVO users){
        System.out.println("old_password = "+users.getOld_password());
        String username = users.getUsername();
        String password = users.getOld_password();
        String pwd = DigestUtils.md5Hex(password + slat);
        Users user = iUsersService.getByUserPwd(username, pwd);
        if (user == null){
            //原始密码错误
            return Result.fail("原密码错误");
        }
        users.setPassword(DigestUtils.md5Hex(users.getPassword() + slat));
        if (iUsersService.updateUser(users))
            return Result.ok("更新用户信息成功");
        return Result.fail(CodeEnum.USERNAME_EXIST);
    }

    @PostMapping("/avatar/set")
    public Result setAvatar(@ModelAttribute("newAvatarBean") NewAvatarBean newAvatarBean, HttpServletRequest request) throws IOException {
        String path = iUsersService.setAvatar(newAvatarBean, request);
        return Result.ok("上传头像成功", path);
    }

    @GetMapping ("/avatar/get/{userId}")
    public Result getAvatar(@PathVariable Integer userId, HttpServletRequest request) {
        String path = iUsersService.getAvatar(userId, request);
        return Result.ok("获取用户头像路径成功", path);
    }

    @PostMapping ("/fans")
    @LoginRequired
    public Result getFans(@RequestBody QueryPageBean queryPageBean, HttpServletRequest request){
        return Result.ok("获取粉丝成功", iUsersService.getFans(queryPageBean, request));
    }

    @PostMapping ("/followees")
    @LoginRequired
    public Result getFollowees(@RequestBody QueryPageBean queryPageBean, HttpServletRequest request){
        return Result.ok("获取关注成功", iUsersService.getFollowees(queryPageBean, request));
    }

    @PostMapping("/userInfo/{userId}")
    public Result getUserInfo(@PathVariable Integer userId, @RequestBody Users user){
        return Result.ok("获取用户个人主页成功", iUsersService.getUserInfo(userId, user));
    }

    @PostMapping("/articles/{userId}")
    public Result getUserArticles(@PathVariable Integer userId, @RequestBody QueryPageBean queryPageBean){
        return Result.ok("获取用户文章成功", iArticlesService.getByUser(queryPageBean, userId));
    }
}

