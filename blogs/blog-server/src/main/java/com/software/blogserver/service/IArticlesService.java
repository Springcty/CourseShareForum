package com.software.blogserver.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software.blogcommon.entity.NewArticleBean;
import com.software.blogcommon.entity.NewAttachBean;
import com.software.blogcommon.entity.QueryPageBean;
import com.software.blogserver.dto.BlogInfoDTO;
import com.software.blogserver.entity.Articles;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software.blogserver.entity.Users;
import com.software.blogserver.vo.ArticlesShowVO;
import com.software.blogserver.vo.ArticlesVO;
import jakarta.servlet.http.HttpServletRequest;
import com.software.blogcommon.entity.NewAttachBean;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yzf
 * @since 2023-05-15
 */
public interface IArticlesService extends IService<Articles> {
    public Page<ArticlesVO> getByCategory(QueryPageBean queryPageBean);

    public Page<ArticlesVO> getByLabel(QueryPageBean queryPageBean);

    public Page<ArticlesVO> getHomeArticles(QueryPageBean queryPageBean);

    public List<ArticlesVO> getLatest();

    public Page<ArticlesVO> searchPage(QueryPageBean queryPageBean);

    void newArticle(NewArticleBean newArticleBean, HttpServletRequest request) throws IOException;

    void addLikes(Integer blogId, Integer delta);

    void addStars(Integer blogId, Integer delta);

    void updateArticleViews(Integer id);

    void addArticleViews(Integer id);

    ArticlesShowVO getOneById(Integer id, Users users);

    public String uploadAttach(NewAttachBean newAttachBean, HttpServletRequest request) throws IOException;

    boolean checkVerified(Integer id);

    boolean checkAuthor(Integer id, Users user);
    //------------------------后台系统-----------------------------
    Page<ArticlesVO> getByUser(QueryPageBean queryPageBean, Integer id);

    void updateVerified(Integer id);

    void updateSelected(Integer id);

    boolean deleteById(Integer id);

    void deleteByIds(List<Integer> ids);

    BlogInfoDTO getBlogInfo();

    Page<ArticlesVO> getAdminArticles(QueryPageBean queryPageBean);

}
