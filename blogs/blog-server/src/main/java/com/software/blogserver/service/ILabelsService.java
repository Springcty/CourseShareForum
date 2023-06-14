package com.software.blogserver.service;

import com.software.blogserver.entity.Labels;
import com.baomidou.mybatisplus.extension.service.IService;
import com.software.blogserver.vo.LabelsVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yzf
 * @since 2023-05-15
 */
public interface ILabelsService extends IService<Labels> {

    public List<LabelsVO> getLabelCount();

    boolean updateOrInsert(Labels labels);
}
