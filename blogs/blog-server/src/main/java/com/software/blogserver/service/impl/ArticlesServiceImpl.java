package com.software.blogserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software.blogcommon.constant.RedisConst;
import com.software.blogcommon.entity.NewArticleBean;
import com.software.blogcommon.entity.NewAttachBean;
import com.software.blogcommon.entity.QueryPageBean;
import com.software.blogserver.dto.BlogInfoDTO;
import com.software.blogserver.dto.BlogRankDTO;
import com.software.blogserver.dto.BlogStatisticsDTO;
import com.software.blogserver.entity.*;
import com.software.blogserver.file.FileOperation;
import com.software.blogserver.mapper.*;
import com.software.blogserver.service.IArticlesService;
import com.software.blogserver.file.FileOperation.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software.blogserver.utils.MarkdownUtils;
import com.software.blogserver.utils.RedisUtils;
import com.software.blogserver.vo.ArticlesShowVO;
import com.software.blogserver.vo.ArticlesVO;
import com.software.blogserver.vo.CategoriesVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.software.blogcommon.constant.RedisConst.BLOG_SET;
import static com.software.blogcommon.constant.RedisConst.BLOG_VIEWS_COUNT;
import static java.lang.Math.abs;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yzf
 * @since 2023-05-15
 */
@Service
public class ArticlesServiceImpl extends ServiceImpl<ArticlesDao, Articles> implements IArticlesService {
    @Autowired
    private ArticlesDao articlesDao;

    @Autowired
    private Blog_labelDao blogLabelDao;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private CategoriesDao categoriesDao;

    @Autowired
    private LabelsDao labelsDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private Like_itemsDao likeItemsDao;

    @Autowired
    private Star_itemsDao starItemsDao;

    @Autowired
    private Follow_itemsDao followItemsDao;

    @Autowired
    private FileOperation fileOperation;

    @Autowired
    private HttpSession session;

    @Autowired
    private RedisUtils redisUtils;
    /**
     * 通过类别分页找到文章，并按浏览量从大到小
     * @param queryPageBean
     * @return
     */
    @Override
    public Page<ArticlesVO> getByCategory(QueryPageBean queryPageBean) {
        Page<ArticlesVO> page = new Page<>(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        QueryWrapper<Articles> qw = new QueryWrapper<>();
        qw.eq(queryPageBean.getCategoryId()!=null, "category_id", queryPageBean.getCategoryId());
        qw.eq("verified", true);
        page.setTotal(articlesDao.selectCount(qw));
        page.setRecords(articlesDao.selectByCategory(queryPageBean));
        return page;
    }

    /**
     * 通过标签分页找到文章，并按浏览量从小到大
     * @param queryPageBean
     * @return
     */
    @Override
    public Page<ArticlesVO> getByLabel(QueryPageBean queryPageBean) {
        Page<ArticlesVO> page = new Page<>(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        QueryWrapper<Blog_label> wrapper = new QueryWrapper<>();
        wrapper.eq(queryPageBean.getLabelId()!=null, "label_id", queryPageBean.getLabelId());
        page.setTotal(articlesDao.selectCountByLabel(queryPageBean));
        page.setRecords(articlesDao.selectByLabel(queryPageBean));
        return page;
    }

    /**
     * 获取首页文章，可按照最新，最热排序
     * @param queryPageBean
     * @return
     */
    @Override
    public Page<ArticlesVO> getHomeArticles(QueryPageBean queryPageBean) {
        QueryWrapper<Articles> qw = new QueryWrapper<>();
        qw.eq("verified", true);
        Page<ArticlesVO> page =new Page<>();
        page.setTotal(articlesDao.selectCount(qw));
        if (Objects.equals(queryPageBean.getSort(), "new")) {
            page.setRecords(articlesDao.selectPageNew(queryPageBean));
        } else {
            page.setRecords(articlesDao.selectPageHot(queryPageBean));
        }
        return page;
    }

    @Override
    public List<ArticlesVO> getLatest() {
        return articlesDao.selectLatest();
    }

    @Override
    public Page<ArticlesVO> searchPage(QueryPageBean queryPageBean) {
        QueryWrapper<Articles> qw = new QueryWrapper<>();
        qw.like(queryPageBean.getQueryString() != null, "content", queryPageBean.getQueryString()).or()
                .like(queryPageBean.getQueryString() != null, "title", queryPageBean.getQueryString()).or()
                .like(queryPageBean.getQueryString() != null, "descript", queryPageBean.getQueryString()).or();
        qw.eq("verified", true);
        Page<ArticlesVO> page =new Page<>();
        page.setTotal(articlesDao.selectCount(qw));
        page.setRecords(articlesDao.selectSearch(queryPageBean));
        return page;
    }

    @Override
    public void newArticle(NewArticleBean newArticleBean, HttpServletRequest request) throws IOException {
        //获取当前时间和作者id
        LocalDateTime time = LocalDateTime.now();
        Integer authorId = newArticleBean.getAuthorId();
        //获取文章内容
        String content = newArticleBean.getContent();
        //更新articles表
        Articles articles = new Articles();
        int id = abs((int) IdWorker.getId(articles));
        articles.setId(id);
        articles.setVerified(false);
        articles.setSelected(false);
        articles.setContent(content);
        articles.setTime(time);
        articles.setCategoryid(newArticleBean.getCategoryId());
        articles.setTitle(newArticleBean.getTitle());
        articles.setDescript(newArticleBean.getDescript());
        articles.setLikes(0);
        articles.setStars(0);
        articles.setComments(0);
        articles.setAuthorid(authorId);
        articles.setViews(0);
        articlesDao.insert(articles);

        //更新blog_label表
        List<Integer> labelIds = newArticleBean.getLabelIds();
        for (Integer labelId: labelIds) {
            Blog_label blog_label = new Blog_label();
            blog_label.setArticleid(id);
            blog_label.setLabelid(labelId);
            blogLabelDao.insert(blog_label);
        }
    }

    @Override
    public void addLikes(Integer blogId, Integer delta) {
        Articles articles = articlesDao.selectById(blogId);
        articles.setLikes(articles.getLikes()+delta);
        articlesDao.updateById(articles);
    }

    @Override
    public void addStars(Integer blogId, Integer delta) {
        Articles articles = articlesDao.selectById(blogId);
        articles.setStars(articles.getStars()+delta);
        articlesDao.updateById(articles);
    }

    @Override
    public void updateArticleViews(Integer id) {
        Set<Integer> blogSet = (Set<Integer>) Optional.ofNullable(session.getAttribute(BLOG_SET)).orElse(new HashSet<>());
        if (!blogSet.contains(id)) {    // 代表是第一次
            blogSet.add(id);
            session.setAttribute(BLOG_SET, blogSet);
            // 浏览量+1
            redisUtils.zIncr(BLOG_VIEWS_COUNT, id, 1D);
            addArticleViews(id);
        }
    }

    @Override
    public void addArticleViews(Integer id){
        Articles articles = articlesDao.selectById(id);
        articles.setViews(articles.getViews() + 1);
        articlesDao.updateById(articles);
    }

    @Override
    public ArticlesShowVO getOneById(Integer id, Users users) {
        Articles articles = articlesDao.selectById(id);
        ArticlesShowVO articlesShowVO = new ArticlesShowVO();
        BeanUtils.copyProperties(articles, articlesShowVO);
        Users author = usersDao.selectById(articles.getAuthorid());
        Categories categories = categoriesDao.selectById(articles.getCategoryid());
        articlesShowVO.setAvatar(author.getAvatar());
        articlesShowVO.setUsername(author.getUsername());
        articlesShowVO.setCategoryName(categories.getCategoryname());
        articlesShowVO.setLabelNameList(labelsDao.getArticleLabel(articlesShowVO.getId()).stream().map(Labels::getLabelname).collect(Collectors.toList()));
        if (articlesShowVO.getContent() != null)
            articlesShowVO.setContent(MarkdownUtils.markdownToHtmlExtensions(articlesShowVO.getContent()));

        Like_items like_items = likeItemsDao.selectOne(
                new QueryWrapper<Like_items>().eq("article_id", id).eq("user_id", users.getId()));
        Star_items star_items = starItemsDao.selectOne(
                new QueryWrapper<Star_items>().eq("article_id", id).eq("user_id", users.getId()));
        Follow_items follow_items = followItemsDao.selectOne(
                new QueryWrapper<Follow_items>().eq("followed_id", author.getId()).eq("follower_id", users.getId()));
        articlesShowVO.setLiked(like_items != null);
        articlesShowVO.setStared(star_items != null);
        articlesShowVO.setFollowed(follow_items != null);
        return articlesShowVO;
    }

    @Override
    public String uploadAttach(NewAttachBean newAttachBean, HttpServletRequest request) throws IOException {
        //获取当前时间和所属文章id，组合生成文件名
        LocalDateTime time = LocalDateTime.now();
        Integer articleId = newAttachBean.getArticleId();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String originalName = newAttachBean.getContent().getOriginalFilename();
        assert originalName != null;
        String suffix = originalName.substring(originalName.indexOf('.')); //获取后缀
        String fileName = time.format(fmt) + articleId + suffix;

        //上传文件
        MultipartFile file = newAttachBean.getContent();
        String path = request.getServletContext().getRealPath("/attaches/");
        fileOperation.saveFile(file, path, fileName);

        return path + fileName;
    }

    @Override
    public boolean checkVerified(Integer id) {
        Articles articles = articlesDao.selectById(id);
        return articles.getVerified();
    }

    @Override
    public boolean checkAuthor(Integer id, Users user) {
        Articles articles = articlesDao.selectById(id);
        return Objects.equals(user.getId(), articles.getAuthorid());
    }

    /**
     * 根据用户名来找到博客
     * @param queryPageBean
     * @param id
     * @return
     */
    @Override
    public Page<ArticlesVO> getByUser(QueryPageBean queryPageBean, Integer id) {
        QueryWrapper<Articles> qw = new QueryWrapper<>();
        qw.eq("author_id", id);
        Page<ArticlesVO> page =new Page<>();
        page.setTotal(articlesDao.selectCount(qw));
        Integer start = queryPageBean.getPageSize() * (queryPageBean.getCurrentPage() - 1);
        page.setRecords(articlesDao.selectByUser(id, start, queryPageBean.getPageSize()));
        return page;
    }

    @Override
    public void updateVerified(Integer id) {
        Articles articles = articlesDao.selectById(id);
        articles.setVerified(!articles.getVerified());
        articlesDao.updateById(articles);
        return;
    }

    @Override
    public void updateSelected(Integer id) {
        Articles articles = articlesDao.selectById(id);
        articles.setSelected(!articles.getSelected());
        articlesDao.updateById(articles);
        return;
    }

    @Override
    public boolean deleteById(Integer id) {
        //1、删除blog_label
        blogLabelDao.delete(new QueryWrapper<Blog_label>().eq("article_id", id));
        //2、删除comment,like,star
        commentDao.delete(new QueryWrapper<Comment_items>().eq("article_id", id));
        likeItemsDao.delete(new QueryWrapper<Like_items>().eq("article_id", id));
        starItemsDao.delete(new QueryWrapper<Star_items>().eq("article_id", id));
        //3、删除articles
        articlesDao.deleteById(id);
        return true;
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        blogLabelDao.delete(new LambdaQueryWrapper<Blog_label>().in(Blog_label::getArticleid, ids));
        commentDao.delete(new LambdaQueryWrapper<Comment_items>().in(Comment_items::getArticleid, ids));
        likeItemsDao.delete(new LambdaQueryWrapper<Like_items>().in(Like_items::getArticle_id, ids));
        starItemsDao.delete(new LambdaQueryWrapper<Star_items>().in(Star_items::getArticle_id, ids));
        articlesDao.deleteBatchIds(ids);
    }

    @Override
    public BlogInfoDTO getBlogInfo() {
        // 查询访问量
        Integer viewsCount = articlesDao.getViews();
        // 查询用户量
        Integer userCount = Math.toIntExact(usersDao.selectCount(null));
        // 查询文章量
        Integer articleCount = Math.toIntExact(articlesDao.selectCount(null));
        // 查询文章统计
        List<BlogStatisticsDTO> blogStatisticsList = articlesDao.listArticleStatistics();
        // 查询分类数据
        List<CategoriesVO> categoryList = categoriesDao.getCategoryCount();
        // 查询标签数据
        List<Labels> labelList = labelsDao.selectList(null);
        // 查询博客浏览量前五
        List<BlogRankDTO> blogRankDTOList = articlesDao.selectList(new LambdaQueryWrapper<Articles>()
                        .select(Articles::getId, Articles::getTitle, Articles::getViews)
                        .last("limit 5").orderByDesc(Articles::getViews))
                .stream().map(blog -> BlogRankDTO.builder()
                        .title(blog.getTitle())
                        .views(blog.getViews())
                        .build())
                .sorted(Comparator.comparingInt(BlogRankDTO::getViews).reversed())
                .collect(Collectors.toList());
        BlogInfoDTO blogInfoDTO = BlogInfoDTO.builder()
                .articleStatisticsList(blogStatisticsList)
                .categoryList(categoryList)
                .viewsCount(viewsCount)
                .userCount(userCount)
                .articleCount(articleCount)
                .blogRankDTOList(blogRankDTOList)
                .labelList(labelList)
                .build();
        return blogInfoDTO;
    }

    @Override
    public Page<ArticlesVO> getAdminArticles(QueryPageBean queryPageBean) {
        Page<ArticlesVO> page =new Page<>();
        page.setRecords(articlesDao.selectPageAdmin(queryPageBean));
        page.setTotal(page.getRecords().size());
        return page;
    }


}
