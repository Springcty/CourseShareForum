package com.software.blogserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software.blogcommon.entity.QueryPageBean;
import com.software.blogserver.entity.Articles;
import com.software.blogserver.entity.Star_items;
import com.software.blogserver.mapper.Star_itemsDao;
import com.software.blogserver.service.IStar_itemsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software.blogserver.vo.ArticlesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yzf
 * @since 2023-05-15
 */
@Service
public class Star_itemsServiceImpl extends ServiceImpl<Star_itemsDao, Star_items> implements IStar_itemsService {

    @Autowired
    private Star_itemsDao starItemsDao;

    @Override
    public Page<ArticlesVO> getByUser(QueryPageBean queryPageBean, Integer id) {
        QueryWrapper<Star_items> qw = new QueryWrapper<>();
        qw.eq("user_id", id);
        Page<ArticlesVO> page =new Page<>();
        page.setTotal(starItemsDao.selectCount(qw));
        Integer start = queryPageBean.getPageSize() * (queryPageBean.getCurrentPage() - 1);
        page.setRecords(starItemsDao.selectByUser(id, start, queryPageBean.getPageSize()));
        return page;
    }
}
