package com.software.blogserver.service.impl;

import com.software.blogserver.entity.Like_items;
import com.software.blogserver.mapper.Like_itemsDao;
import com.software.blogserver.service.ILike_itemsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class Like_itemsServiceImpl extends ServiceImpl<Like_itemsDao, Like_items> implements ILike_itemsService {

}
