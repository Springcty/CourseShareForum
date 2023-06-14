package com.software.blogserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software.blogcommon.entity.QueryPageBean;
import com.software.blogserver.entity.Articles;
import com.software.blogserver.entity.Comment_items;
import com.software.blogserver.entity.Users;
import com.software.blogserver.mapper.ArticlesDao;
import com.software.blogserver.mapper.CommentDao;
import com.software.blogserver.mapper.UsersDao;
import com.software.blogserver.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software.blogserver.vo.CommentAdminVO;
import com.software.blogserver.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yzf
 * @since 2023-05-15
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentDao, Comment_items> implements ICommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private ArticlesDao articlesDao;
    @Override
    public List<CommentVO> getByBlogid(Integer blogId) {
        return commentDao.getByBlogid(blogId);
    }

    @Override
    public CommentVO replyComment(Comment_items comment, Integer userId) {
        Articles articles = articlesDao.selectById(comment.getArticleid());
        articles.setComments(articles.getComments()+1);
        articlesDao.updateById(articles);

        comment.setUserid(userId);
        comment.setId((int) IdWorker.getId(comment.getClass()));
        commentDao.insert(comment);
        CommentVO commentVO = new CommentVO();
        commentVO.setId(comment.getId());
        commentVO.setUserid(userId);
        commentVO.setTime(comment.getTime());
        commentVO.setArticleid(comment.getArticleid());
        commentVO.setContent(comment.getContent());
        Users user = usersDao.selectById(userId);
        commentVO.setAvatar(user.getAvatar());
        commentVO.setUsername(user.getUsername());
        return commentVO;
    }

    @Override
    public boolean deleteComment(Integer blogId, Integer commentId, Integer userId) {
        Articles articles = articlesDao.selectById(blogId);
        articles.setComments(articles.getComments()-1);
        articlesDao.updateById(articles);

        QueryWrapper<Comment_items> qw = new QueryWrapper<>();
        qw.eq("user_id", userId);
        qw.eq("article_id", blogId);
        qw.eq("id", commentId);
        if (commentDao.delete(qw) > 0)
            return true;
        return false;
    }

    @Override
    public Page<CommentAdminVO> getAllComment(QueryPageBean queryPageBean) {
        Page<CommentAdminVO> page = new Page<>();
        page.setTotal(commentDao.selectCount(null));
        Integer start = queryPageBean.getPageSize() * (queryPageBean.getCurrentPage() - 1);
        page.setRecords(commentDao.selectByPage(start, queryPageBean.getPageSize()));
        return page;
    }
}
