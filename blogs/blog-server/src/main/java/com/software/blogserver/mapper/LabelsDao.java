package com.software.blogserver.mapper;

import com.software.blogserver.entity.Labels;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.software.blogserver.vo.LabelsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yzf
 * @since 2023-05-15
 */
@Mapper
public interface LabelsDao extends BaseMapper<Labels> {
    List<LabelsVO> getLabelCount();

    List<Labels> getArticleLabel(@Param("id") Integer id);

    Integer getVerified(@Param("id") Integer id);
}
