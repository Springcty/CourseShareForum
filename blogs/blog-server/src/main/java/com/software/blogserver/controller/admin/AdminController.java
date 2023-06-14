package com.software.blogserver.controller.admin;

import com.software.blogcommon.entity.QueryPageBean;
import com.software.blogcommon.entity.Result;
import com.software.blogserver.service.IArticlesService;
import com.software.blogserver.service.ICommentService;
import com.software.blogserver.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ICommentService iCommentService;

    @Autowired
    private IUsersService iUsersService;

    @PostMapping("/commentsList")
    public Result getAdminComments(@RequestBody QueryPageBean queryPageBean){
        return Result.ok("读取评论列表成功", iCommentService.getAllComment(queryPageBean));
    }

    @PostMapping("/usersList")
    public Result getAdminUsers(@RequestBody QueryPageBean queryPageBean){
        return Result.ok("获取用户列表成功", iUsersService.getAllUser(queryPageBean));
    }

    @GetMapping("/privilege/{id}/{type}")
    public Result changePrivilege(@PathVariable Integer id, @PathVariable Integer type){
        iUsersService.updatePrivilege(id, type);
        return Result.ok("修改用户权限成功");
    }

    @DeleteMapping("/comments/deleteId/{id}")
    public Result deleteCommentById(@PathVariable Integer id){
        iCommentService.removeById(id);
        return Result.ok("后台删除评论成功");
    }

    @PostMapping("comments/deleteIds")
    public Result deleteCommentByIds(@RequestBody List<Integer> ids){
        iCommentService.removeByIds(ids);
        return Result.ok("后台批量删除评论成功");
    }

}
