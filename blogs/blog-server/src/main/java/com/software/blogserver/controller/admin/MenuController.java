package com.software.blogserver.controller.admin;

import com.software.blogcommon.entity.Result;
import com.software.blogserver.annotation.LoginRequired;
import com.software.blogserver.entity.Users;
import com.software.blogserver.service.IMenuService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
// @CrossOrigin
public class MenuController {
    @Autowired
    private IMenuService iMenuService;

    @LoginRequired
    @GetMapping("/admin/listUserMenus")
    public Result listUserMenus(HttpServletRequest request){
        Users user = (Users) request.getAttribute("currentUser");
        return Result.ok("获取用户菜单成功", iMenuService.listUserMenus(user.getId()));
    }

    @LoginRequired
    @GetMapping("/admin/listAdminMenus")
    public Result listAdminMenus(HttpServletRequest request){
        Users user = (Users) request.getAttribute("currentUser");
        return Result.ok("获取管理员菜单成功", iMenuService.listAdminMenus(user.getId()));
    }
}
