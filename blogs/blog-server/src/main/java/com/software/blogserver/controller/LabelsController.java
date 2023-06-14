package com.software.blogserver.controller;


import com.software.blogcommon.entity.Result;
import com.software.blogserver.entity.Categories;
import com.software.blogserver.entity.Labels;
import com.software.blogserver.service.ICategoriesService;
import com.software.blogserver.service.ICommentService;
import com.software.blogserver.service.ILabelsService;
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
@RequestMapping("/labels")
public class LabelsController {
    @Autowired
    private ILabelsService iLabelsService;

    @PostMapping("/admin/updateOrInsert")
    public Result updateOrInsert(@RequestBody Labels labels){
        boolean flag = iLabelsService.updateOrInsert(labels);
        if (flag)
            return Result.ok("添加或更改标签成功");
        return Result.fail("添加失败，要添加或更改的标签已存在");
    }
}

