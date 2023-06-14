package com.software.blogserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.software.blogserver.entity.Articles;
import com.software.blogserver.entity.Categories;
import com.software.blogserver.entity.Labels;
import com.software.blogserver.mapper.LabelsDao;
import com.software.blogserver.service.ILabelsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software.blogserver.vo.CategoriesVO;
import com.software.blogserver.vo.LabelsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
public class LabelsServiceImpl extends ServiceImpl<LabelsDao, Labels> implements ILabelsService {
    @Autowired
    private LabelsDao labelsDao;
    @Override
    public List<LabelsVO> getLabelCount() {
        List<LabelsVO> labelCount = labelsDao.getLabelCount();
        for (int i=0; i<labelCount.size(); i++){
            Integer count2 = labelsDao.getVerified(labelCount.get(i).getId());
            Integer count1 = labelCount.get(i).getLabelCount();
            labelCount.get(i).setLabelCount(count1 - count2);
        }
        Collections.sort(labelCount, (l1, l2) -> l2.getLabelCount()-l1.getLabelCount());
        return labelCount;
    }

    @Override
    public boolean updateOrInsert(Labels labels) {
        Labels labelsDB = labelsDao.selectOne(new LambdaQueryWrapper<Labels>().eq(Labels::getLabelname, labels.getLabelname()).select(Labels::getId));
        if (Objects.nonNull(labelsDB)) {
            return false;
        }
        Labels labelsAdd = new Labels(labels.getId(), labels.getLabelname());
        this.saveOrUpdate(labelsAdd);
        return true;
    }
}
