package com.software.blogserver.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.software.blogcommon.entity.QueryPageBean;
import com.software.blogserver.entity.Star_items;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software.blogserver.vo.ArticlesVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yzf
 * @since 2023-05-15
 */
public interface IStar_itemsService extends IService<Star_items> {

    Page<ArticlesVO> getByUser(QueryPageBean queryPageBean, Integer id);
}
