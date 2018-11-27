package com.one.pig.service.user.impl;


import com.one.pig.core.bean.UserDto;
import com.one.pig.service.user.MenuService;
import com.one.pig.system.dao.MenuMapper;
import com.one.pig.system.dao.UserMapper;
import com.one.pig.system.model.Menu;
import com.sun.javafx.menu.MenuBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单服务
 *
 * @author csy
 * @date 2017-05-05 22:20
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    MenuMapper menuMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public List<MenuBase> getMenus(UserDto userDto) {
        // List<MenuBase> menuBaseList = new ArrayList<>();
        // User user = userMapper.selectByPrimaryKey(userDto.getUserId());
        // Integer[] roleArray = ConvertUtils.toIntArray(user.getRoleId());// 角色集合
        // for (int roleId : roleArray) {
        //     List<MenuBase> menu = menuMapper.getMenus(userDto.getUserId(), roleId);
        //     menuBaseList.addAll(menu);
        // }
        // if (CollectionUtils.isEmpty(menuBaseList))
        //     return null;
        // Set<MenuBase> menus = menuBaseList.stream().map(target -> new MenuBase(target.getId(), target.getUrl(), target.getCode(), target.getNum(), target.getPcode(), target.getIcon(), target.getIsmenu(), target.getName(), target.getStatus(), target.getTips(), target.getLevels())).collect(Collectors.toSet());
        // return MenuUtils.format(MenuEnums.LevelEnum.SECOND, menus).stream().collect(Collectors.toList());
        return null;
    }

    @Override
    public void delMenu(Integer menuId) {
        //删除菜单
        this.menuMapper.deleteByPrimaryKey(menuId);
        //删除关联的relation
        this.menuMapper.deleteRelationByMenu(menuId);
    }

    @Override
    public void delMenuContainSubMenus(Integer menuId) {

        Menu menu = menuMapper.selectByPrimaryKey(menuId);

        //删除当前菜单
        delMenu(menuId);

        //删除所有子菜单
        Example example = new Example(Menu.class);
        example.createCriteria().andLike("pcodes", "%[" + menu.getCode() + "]%");
        List<Menu> menus = menuMapper.selectByExample(example);
        for (Menu temp : menus) {
            delMenu(temp.getId());
        }
    }
}
