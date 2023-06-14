package com.software.blogserver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.software.blogserver.dto.UserMenuDTO;
import com.software.blogserver.entity.Menu;

import java.util.List;

public interface IMenuService extends IService<Menu> {
    List<UserMenuDTO> listUserMenus(Integer id);

    List<UserMenuDTO> listAdminMenus(Integer id);
}
