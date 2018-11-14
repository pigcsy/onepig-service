package com.one.pig.service.user;

import com.one.pig.core.bean.UserDto;
import com.sun.javafx.menu.MenuBase;

import java.util.List;

/**
 * 菜单服务
 *
 * @author csy
 * @date 2017-05-05 22:19
 */
public interface MenuService {


    /**
     * 登陆时获取所有侧边栏
     *
     * @param userDto
     * @return
     */
    List<MenuBase> getMenus(UserDto userDto);

    /**
     * 删除菜单
     *
     * @author csy
     * @Date 2017/5/5 22:20
     */
    void delMenu(Integer menuId);

    /**
     * 删除菜单包含所有子菜单
     *
     * @author csy
     * @Date 2017/6/13 22:02
     */
    void delMenuContainSubMenus(Integer menuId);
}
