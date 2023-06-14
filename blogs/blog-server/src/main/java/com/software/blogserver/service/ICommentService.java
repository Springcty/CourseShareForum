package com.software.blogserver.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software.blogcommon.entity.QueryPageBean;
import com.software.blogserver.entity.Comment_items;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software.blogserver.vo.CommentAdminVO;
import com.software.blogserver.vo.CommentVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yzf
 * @since 2023-05-15
 */
public interface ICommentService extends IService<Comment_items> {

    public List<CommentVO> getByBlogid(Integer blogId);

    public CommentVO replyComment(Comment_items comment, Integer userId);

    public boolean deleteComment(Integer blogId, Integer commentId, Integer userId);

    public Page<CommentAdminVO> getAllComment(QueryPageBean queryPageBean);
}
