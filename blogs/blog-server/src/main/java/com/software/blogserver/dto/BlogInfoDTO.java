package com.software.blogserver.dto;

import com.software.blogserver.entity.Labels;
import com.software.blogserver.vo.CategoriesVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 博客后台信息
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogInfoDTO {
    /**
     * 访问量
     */
    private Integer viewsCount;
    /**
     * 用户量
     */
    private Integer userCount;
    /**
     * 文章量
     */
    private Integer articleCount;
    /**
     * 分类统计
     */
    private List<CategoriesVO> categoryList;
    /**
     * 标签列表
     */
    private List<Labels> labelList;
    /**
     * 文章统计列表
     */
    private List<BlogStatisticsDTO> articleStatisticsList;
    /**
     * 文章浏览量排行
     */
    private List<BlogRankDTO> blogRankDTOList;

}
