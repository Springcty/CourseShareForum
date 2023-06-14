package com.software.blogserver.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.software.blogcommon.entity.Result;
import com.software.blogserver.annotation.LoginRequired;
import com.software.blogserver.entity.Follow_items;
import com.software.blogserver.entity.Like_items;
import com.software.blogserver.entity.Users;
import com.software.blogserver.service.IArticlesService;
import com.software.blogserver.service.IFollow_itemsService;
import com.software.blogserver.service.ILike_itemsService;
import com.software.blogserver.service.IUsersService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yzf
 * @since 2023-05-15
 */
@RestController
@RequestMapping("/follow")
public class Follow_itemsController {

    @Autowired
    private IFollow_itemsService iFollow_itemsService;

    @GetMapping("/{userId}")
    @LoginRequired
    public Result addFollowUser(@PathVariable Integer userId, HttpServletRequest request){
        Users user = (Users) request.getAttribute("currentUser");
        //1、(userID, user) (followed, follower)添加到follow_item表格
        Follow_items follow_items = new Follow_items();
        follow_items.setFollowedid(userId);
        follow_items.setFollowerid(user.getId());
        iFollow_itemsService.save(follow_items);
        return Result.ok("关注成功");
    }


    @DeleteMapping("/{userId}")
    @LoginRequired
    public Result deleteFollowUser(@PathVariable Integer userId, HttpServletRequest request){
        Users user = (Users) request.getAttribute("currentUser");
        //1、确认follow_items中是否包含
        QueryWrapper<Follow_items> qw = new QueryWrapper<>();
        qw.eq("followed_id", userId);
//        follower:关注者
        qw.eq("follower_id", user.getId());
        if (iFollow_itemsService.count(qw) == 0){
            return Result.fail("用户未被关注");
        }
        //2、移除follow_items
        iFollow_itemsService.remove(qw);
        return Result.ok("取消关注成功");
    }
}

