package com.software.blogserver.controller;


import com.software.blogcommon.entity.*;
import com.software.blogserver.annotation.LoginRequired;
import com.software.blogserver.entity.Articles;
import com.software.blogserver.entity.Users;
import com.software.blogserver.service.IArticlesService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTML;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yzf
 * @since 2023-05-15
 */
@RestController
// @CrossOrigin(origins = {"*"})
@RequestMapping("/articles")
public class ArticlesController {
    @Autowired
    private IArticlesService iArticlesService;

    @PostMapping("/{id}")
    public Result getById(@PathVariable Integer id, @RequestBody Users user){
        if (user == null || !user.getPriviledged() || !iArticlesService.checkAuthor(id, user)){
            if (!iArticlesService.checkVerified(id))
                return Result.fail("文章未审核");
            iArticlesService.updateArticleViews(id);
        }
        return Result.ok("读取文章成功", iArticlesService.getOneById(id, user));
    }

    @PostMapping("/category")
    public Result getByCategory(@RequestBody QueryPageBean queryPageBean){
        return Result.ok("根据类别获取成功", iArticlesService.getByCategory(queryPageBean));
    }

    @PostMapping ("/label")
    public Result getByLabel(@RequestBody QueryPageBean queryPageBean){
        return Result.ok("根据标签获取成功", iArticlesService.getByLabel(queryPageBean));
    }

    @PostMapping ("/new")
    @LoginRequired
    public Result newArticle(@RequestBody NewArticleBean newArticleBean, HttpServletRequest request) throws IOException {
        iArticlesService.newArticle(newArticleBean, request);
        return Result.ok("创建新文章成功");
    }

    @PostMapping("/findPage")
    @LoginRequired
    public Result findByUser(@RequestBody QueryPageBean queryPageBean, HttpServletRequest request){
        Users user = (Users) request.getAttribute("currentUser");
        return Result.ok("后台查询用户文章成功", iArticlesService.getByUser(queryPageBean, user.getId()));
    }

    @PostMapping ("/attach")
    @LoginRequired
    public Result uploadAttach(@ModelAttribute("newAttachBean") NewAttachBean newAttachBean, HttpServletRequest request) throws IOException {
        String path = iArticlesService.uploadAttach(newAttachBean, request);
        return Result.ok("上传附件成功", path);
    }

}

