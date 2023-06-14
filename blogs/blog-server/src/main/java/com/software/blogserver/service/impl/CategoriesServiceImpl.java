package com.software.blogserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.software.blogserver.entity.Articles;
import com.software.blogserver.entity.Categories;
import com.software.blogserver.mapper.ArticlesDao;
import com.software.blogserver.mapper.CategoriesDao;
import com.software.blogserver.service.ICategoriesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software.blogserver.vo.CategoriesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yzf
 * @since 2023-05-15
 */
@Service
public class CategoriesServiceImpl extends ServiceImpl<CategoriesDao, Categories> implements ICategoriesService {
    @Autowired
    private CategoriesDao categoriesDao;

    @Autowired
    private ArticlesDao articlesDao;

    @Override
    public boolean updateOrInsert(Categories categories) {
        Categories categoriesDB = categoriesDao.selectOne(new LambdaQueryWrapper<Categories>().eq(Categories::getCategoryname, categories.getCategoryname()).select(Categories::getId));
        if (Objects.nonNull(categoriesDB)) {
            return false;
        }
        Categories categoriesAdd = new Categories(categories.getId(), categories.getCategoryname());
        this.saveOrUpdate(categoriesAdd);
        return true;
    }

    @Override
    public List<CategoriesVO> getCategoryCount() {
        List<CategoriesVO> cateCount = categoriesDao.getCategoryCount();
        for (int i=0; i<cateCount.size(); i++){
            QueryWrapper<Articles> qw = new QueryWrapper<>();
            qw.eq("category_id", cateCount.get(i).getId());
            qw.eq("verified", false);
            Integer count2 = Math.toIntExact(articlesDao.selectCount(qw));
            Integer count1 = cateCount.get(i).getCategoryCount();
            cateCount.get(i).setCategoryCount(count1 - count2);
        }
        Collections.sort(cateCount, (l1, l2) -> l2.getCategoryCount()-l1.getCategoryCount());
        return cateCount;
    }
}
