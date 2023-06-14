package com.software.blogserver.controller;


import com.software.blogcommon.entity.Result;
import com.software.blogserver.annotation.LoginRequired;
import com.software.blogserver.entity.Comment_items;
import com.software.blogserver.entity.Users;
import com.software.blogserver.service.ICommentService;
import com.software.blogserver.vo.CommentVO;
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
@RequestMapping("/comment")
// @CrossOrigin
public class CommentController {
    @Autowired
    private ICommentService iCommentService;

    @GetMapping("/{blogId}")
    public Result getCommentList(@PathVariable Integer blogId){
        return Result.ok("获取博客评论成功", iCommentService.getByBlogid(blogId));
    }

    @PostMapping("/reply")
    @LoginRequired
    public Result replyComment(@RequestBody Comment_items comment, HttpServletRequest request){
        Users user = (Users) request.getAttribute("currentUser");
        CommentVO commentVO = iCommentService.replyComment(comment, user.getId());
        return Result.ok("回复评论信息成功", commentVO);
    }

    @DeleteMapping("/delete/{blogId}/{commentId}")
    @LoginRequired
    public Result deletComment(@PathVariable Integer blogId, @PathVariable Integer commentId, HttpServletRequest request){
        Users user = (Users) request.getAttribute("currentUser");
        if (iCommentService.deleteComment(blogId, commentId, user.getId())){
            return Result.ok("删除评论成功");
        }
        return Result.fail("删除评论失败");
    }
}

