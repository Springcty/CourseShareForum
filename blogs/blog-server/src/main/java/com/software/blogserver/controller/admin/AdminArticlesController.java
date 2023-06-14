package com.software.blogserver.controller.admin;

import com.software.blogcommon.entity.QueryPageBean;
import com.software.blogcommon.entity.Result;
import com.software.blogserver.service.IArticlesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/articles")
// @CrossOrigin(origins = {"*"})
public class AdminArticlesController {
    @Autowired
    private IArticlesService iArticlesService;

    @PostMapping("/list")
    public Result getAdminArticles(@RequestBody QueryPageBean queryPageBean){
        queryPageBean.setSort("new");
        return Result.ok("获取文章列表成功", iArticlesService.getAdminArticles(queryPageBean));
    }

    @GetMapping("/verified/{id}")
    public Result updateVerified(@PathVariable Integer id){
        iArticlesService.updateVerified(id);
        return Result.ok("修改审核文章状态成功");
    }

    @GetMapping("/selected/{id}")
    public Result updateSelected(@PathVariable Integer id){
        iArticlesService.updateSelected(id);
        return Result.ok("修改推荐文章状态成功");
    }

    @DeleteMapping("/deleteId/{id}")
    public Result deleteById(@PathVariable Integer id){
        iArticlesService.deleteById(id);
        return Result.ok("删除文章成功");
    }

    @PostMapping("/deleteIds")
    public Result deleteByIds(@RequestBody List<Integer> ids){
        iArticlesService.deleteByIds(ids);
        return Result.ok("批量删除文章成功");
    }

    @GetMapping("/blogInfo")
    public Result getBlogInfo(){
        return Result.ok("获取博客数据成功", iArticlesService.getBlogInfo());
    }
}
