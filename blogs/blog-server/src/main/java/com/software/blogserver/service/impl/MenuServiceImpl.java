package com.software.blogserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.software.blogserver.dto.UserMenuDTO;
import com.software.blogserver.entity.Menu;
import com.software.blogserver.mapper.MenuDao;
import com.software.blogserver.service.IMenuService;
import com.software.blogserver.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.software.blogcommon.constant.CommonConst.ADMIN_MENUS;
import static com.software.blogcommon.constant.CommonConst.USER_MENUS;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, Menu> implements IMenuService {
    @Autowired
    private MenuDao menuDao;

    @Override
    public List<UserMenuDTO> listUserMenus(Integer id) {
        return listMenus(id, USER_MENUS);
    }

    @Override
    public List<UserMenuDTO> listAdminMenus(Integer id) {
        return listMenus(id, ADMIN_MENUS);
    }

    private List<UserMenuDTO> listMenus(Integer id, Integer type) {
        //1、根据用户类别找到对应的menu号
        List<Menu> menuList = menuDao.selectList(new LambdaQueryWrapper<Menu>().eq(Menu::getType, type));
        //2、获取目录列表
        List<Menu> catalogList = listCatalog(menuList);
        //3、获取目录下子菜单
        Map<Integer, List<Menu>> childrenMap = listChildren(menuList);
        return convertUserMenuList(catalogList, childrenMap);
    }

    private List<UserMenuDTO> convertUserMenuList(List<Menu> catalogList, Map<Integer, List<Menu>> childrenMap) {
        return catalogList.stream().map(item -> {
            // 获取目录
            UserMenuDTO userMenuDTO = new UserMenuDTO();
            List<UserMenuDTO> list = new ArrayList<>();
            // 获取目录下的子菜单
            List<Menu> children = childrenMap.get(item.getId());
            if (CollectionUtils.isNotEmpty(children)) {
                // 多级菜单处理
                userMenuDTO = BeanCopyUtils.copyObject(item, UserMenuDTO.class);
                list = children.stream()
                        .sorted(Comparator.comparing(Menu::getOrdernum))
                        .map(menu -> {
                            UserMenuDTO dto = BeanCopyUtils.copyObject(menu, UserMenuDTO.class);
                            dto.setHidden(menu.getIshidden().equals(1));
                            return dto;
                        })
                        .collect(Collectors.toList());
            } else {
                // 一级菜单处理
                userMenuDTO.setPath(item.getPath());
                userMenuDTO.setComponent("Layout");
                list.add(UserMenuDTO.builder()
                        .path("")
                        .name(item.getName())
                        .icon(item.getIcon())
                        .component(item.getComponent())
                        .build());
            }
            userMenuDTO.setHidden(item.getIshidden().equals(1));
            userMenuDTO.setChildren(list);
            return userMenuDTO;
        }).collect(Collectors.toList());
    }

    private List<Menu> listCatalog(List<Menu> menuList) {
        return menuList.stream()
                .filter(item -> Objects.isNull(item.getParent()))
                .sorted(Comparator.comparing(Menu::getOrdernum))
                .collect(Collectors.toList());
    }

    private Map<Integer, List<Menu>> listChildren(List<Menu> menuList) {
        return menuList.stream()
                .filter(item -> Objects.nonNull(item.getParent()))
                .collect(Collectors.groupingBy(Menu::getParent));
    }
}
