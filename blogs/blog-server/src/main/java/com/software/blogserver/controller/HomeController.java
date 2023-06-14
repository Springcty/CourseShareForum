package com.software.blogserver.controller;

import com.software.blogserver.service.IArticlesService;
import com.software.blogserver.service.ICategoriesService;
import com.software.blogserver.service.ILabelsService;
import com.software.blogserver.service.impl.ArticlesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.software.blogcommon.entity.*;
/*
首页展示模块
 */
@RestController
// @CrossOrigin
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private IArticlesService iArticlesService;

    @Autowired
    private ICategoriesService categoriesService;

    @Autowired
    private ILabelsService iLabelsService;
    /*
    根据最新/最热筛选文章
     */
    @PostMapping("/articles")
    public Result getArticles(@RequestBody QueryPageBean queryPageBean){
        Result result = Result.ok("获取首页博客数据成功", iArticlesService.getHomeArticles(queryPageBean));
        return result;
    }

    @GetMapping("/getCategoryCount")
    public Result getCategoryCount(){
        return Result.ok("获取类别信息成功", categoriesService.getCategoryCount());
    }

    @GetMapping("/getLabelCount")
    public Result getLabelCount(){
        return Result.ok("获取标签信息成功", iLabelsService.getLabelCount());
    }

    /**
     * 获取最新博客列表
     * @return
     */
    @GetMapping("/latestList")
    public Result getLatest() {
        return Result.ok("获取最新博客列表成功", iArticlesService.getLatest());
    }

    /**
     * 根据搜索信息，在content中寻找是否有硬性匹配的博客
     * @param queryPageBean
     * @return
     */
    @PostMapping("/search")
    public Result searchPage(@RequestBody QueryPageBean queryPageBean){
        return Result.ok("搜索博客成功", iArticlesService.searchPage(queryPageBean));
    }
}
