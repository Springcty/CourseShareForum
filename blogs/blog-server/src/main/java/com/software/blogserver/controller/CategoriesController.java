package com.software.blogserver.controller;


import com.software.blogcommon.entity.Result;
import com.software.blogserver.entity.Categories;
import com.software.blogserver.service.IArticlesService;
import com.software.blogserver.service.ICategoriesService;
import com.software.blogserver.vo.CategoriesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yzf
 * @since 2023-05-15
 */
@RestController
@RequestMapping("/categories")
public class CategoriesController {
    @Autowired
    private ICategoriesService iCategoriesService;

    @PostMapping("/admin/updateOrInsert")
    public Result updateOrInsert(@RequestBody Categories categories){
        boolean flag = iCategoriesService.updateOrInsert(categories);
        if (flag)
            return Result.ok("添加或更改类别成功");
        return Result.fail("添加失败，要添加或更改的类别已存在");
    }
}

