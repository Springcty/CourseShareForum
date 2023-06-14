package com.software.blogserver.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.software.blogcommon.entity.Result;
import com.software.blogserver.annotation.LoginRequired;
import com.software.blogserver.entity.Like_items;
import com.software.blogserver.entity.Users;
import com.software.blogserver.service.IArticlesService;
import com.software.blogserver.service.ILike_itemsService;
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
@RequestMapping("/likes")
// @CrossOrigin
public class LikesController {
    @Autowired
    private IArticlesService iArticlesService;

    @Autowired
    private ILike_itemsService iLike_itemsService;

    @GetMapping ("/{blogId}")
    @LoginRequired
    public Result addLikeArticle(@PathVariable Integer blogId, HttpServletRequest request){
        Users user = (Users) request.getAttribute("currentUser");
        QueryWrapper<Like_items> qw = new QueryWrapper<>();
        qw.eq("article_id", blogId);
        qw.eq("user_id", user.getId());
        if (iLike_itemsService.count(qw) > 0){
            return Result.fail("文章已被点赞");
        }
        //1、将article中的likes+1
        iArticlesService.addLikes(blogId, 1);
        //2、添加到like_items中
        Like_items like_items = new Like_items();
        like_items.setArticle_id(blogId);
        like_items.setUser_id(user.getId());
        iLike_itemsService.save(like_items);
        return Result.ok("点赞成功");
    }

    @DeleteMapping ("/{blogId}")
    @LoginRequired
    public Result deleteLikeArticle(@PathVariable Integer blogId, HttpServletRequest request){
        Users user = (Users) request.getAttribute("currentUser");
        //1、确认like_items中是否包含
        QueryWrapper<Like_items> qw = new QueryWrapper<>();
        qw.eq("article_id", blogId);
        qw.eq("user_id", user.getId());
        if (iLike_itemsService.count(qw) == 0){
            return Result.fail("文章未被点赞");
        }
        //2、移除like_items和articles_like
        iLike_itemsService.remove(qw);
        iArticlesService.addLikes(blogId, -1);
        return Result.ok("取消点赞成功");
    }
}

