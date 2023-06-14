package com.software.blogserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.software.blogserver.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuDao extends BaseMapper<Menu> {
}
