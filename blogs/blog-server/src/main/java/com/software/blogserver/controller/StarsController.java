package com.software.blogserver.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.software.blogcommon.entity.QueryPageBean;
import com.software.blogcommon.entity.Result;
import com.software.blogserver.annotation.LoginRequired;
import com.software.blogserver.entity.Like_items;
import com.software.blogserver.entity.Star_items;
import com.software.blogserver.entity.Users;
import com.software.blogserver.service.IArticlesService;
import com.software.blogserver.service.IStar_itemsService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.User;
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
@RequestMapping("/stars")
// @CrossOrigin(origins = {"*"})
public class StarsController {
    @Autowired
    private IArticlesService iArticlesService;

    @Autowired
    private IStar_itemsService iStar_itemsService;

    @GetMapping("/{blogId}")
    @LoginRequired
    public Result addStarArticle(@PathVariable Integer blogId, HttpServletRequest request){
        Users user = (Users) request.getAttribute("currentUser");
        QueryWrapper<Star_items> qw = new QueryWrapper<>();
        qw.eq("article_id", blogId);
        qw.eq("user_id", user.getId());
        if (iStar_itemsService.count(qw) > 0){
            return Result.fail("文章已被收藏");
        }
        //1、将article中的star+1
        iArticlesService.addStars(blogId, 1);
        //2、添加到star_items中
        Star_items star_items = new Star_items();
        star_items.setArticle_id(blogId);
        star_items.setUser_id(user.getId());
        iStar_itemsService.save(star_items);
        return Result.ok("收藏成功");
    }

    @PostMapping("/{blogId}")
    @LoginRequired
    public Result deleteStarArticle(@PathVariable Integer blogId, HttpServletRequest request){
        Users user = (Users) request.getAttribute("currentUser");
        //1、确认star_items中是否包含
        QueryWrapper<Star_items> qw = new QueryWrapper<>();
        qw.eq("article_id", blogId);
        qw.eq("user_id", user.getId());
        if (iStar_itemsService.count(qw) == 0){
            return Result.fail("文章未被收藏");
        }
        //2、移除like_items和articles_like
        iStar_itemsService.remove(qw);
        iArticlesService.addStars(blogId, -1);
        return Result.ok("取消收藏成功");
    }

    @PostMapping("/getByUser")
    @LoginRequired
    public Result getByUser(@RequestBody QueryPageBean queryPageBean, HttpServletRequest request){
        Users user = (Users) request.getAttribute("currentUser");
        return Result.ok("查询用户收藏文章成功", iStar_itemsService.getByUser(queryPageBean, user.getId()));
    }

}

