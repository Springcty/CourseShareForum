package com.software.blogserver.service;

import com.software.blogserver.entity.Categories;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software.blogserver.vo.CategoriesVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yzf
 * @since 2023-05-15
 */
public interface ICategoriesService extends IService<Categories> {
    public boolean updateOrInsert(Categories categories);

    public List<CategoriesVO> getCategoryCount();
}
