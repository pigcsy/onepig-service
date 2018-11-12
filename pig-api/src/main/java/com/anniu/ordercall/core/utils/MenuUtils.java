/**
 * Project Name:saas-authentication
 * File Name:MenuFormat.java
 * Package Name:com.saas.authentication.support
 * Date:2016年10月19日下午8:44:19
 * Copyright (c) 2016, 蚂上配件 Ltd. All Rights Reserved.
 */
package com.anniu.ordercall.core.utils;

import com.anniu.ordercall.core.base.MenuBase;
import com.anniu.ordercall.core.enums.MenuEnums;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ClassName: MenuFormat <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 */
public class MenuUtils {

    /**
     * 格式化菜单
     *
     * @param beginLevel 开始菜单等级
     * @param sources
     * @return
     */
    public static Set<MenuBase> format(final MenuEnums.LevelEnum beginLevel, Collection<MenuBase> sources) {
        Set<MenuBase> menuBases = sources.stream().filter(target -> beginLevel.getValue() == target.getLevels())
                .map(target -> new MenuBase(target.getId(), target.getUrl(), target.getCode(), target.getNum(), target.getPcode(), target.getIcon(), target.getIsmenu(), target.getName(), target.getStatus(), target.getTips(), target.getLevels())).sorted().collect(Collectors.toSet());
        menuBases.forEach(target -> {
            target.setSubMenuBases(matchSubMenu(target, sources));
        });
        return menuBases;
    }

    /**
     * 匹配子菜单
     *
     * @param parentMenuBase
     * @param resources
     */
    public static List<MenuBase> matchSubMenu(MenuBase parentMenuBase, final Collection<MenuBase> resources) {
        List<MenuBase> subMenuBases = resources.stream().filter(target -> parentMenuBase.getCode().equalsIgnoreCase(target.getPcode()))
                .map(target -> new MenuBase(target.getId(), target.getUrl(), target.getCode(), target.getNum(), target.getPcode(), target.getIcon(), target.getIsmenu(), target.getName(), target.getStatus(), target.getTips(), target.getLevels())).sorted().collect(Collectors.toList());
        subMenuBases.forEach(target -> {
            List<MenuBase> tempMenuBases = matchSubMenu(target, resources);
            if (CollectionUtils.isNotEmpty(tempMenuBases)) {
                target.setSubMenuBases(tempMenuBases);
            }
        });
        return subMenuBases;
    }

    public static List<MenuBase> format(List<MenuBase> menuBases) {
        List<MenuBase> lists = new ArrayList<>();
        menuBases.stream().filter(menuBase -> MenuEnums.LevelEnum.FIRST.getValue() == menuBase.getLevels()).forEach(parent -> {
            lists.add(parent);
            matchSubResource(parent, menuBases);// 寻找子节点
            menuBases.add(parent);
        });
        Collections.sort(menuBases);
        return menuBases;
    }

    private final static void matchSubResource(MenuBase parent, List<MenuBase> menuBases) {
        List<MenuBase> subMenuBases = new ArrayList<MenuBase>();
        menuBases.stream().filter(menuBase -> parent.getCode().equalsIgnoreCase(menuBase.getPcode())).forEach(menuBase -> {
            matchSubResource(menuBase, menuBases);
            subMenuBases.add(menuBase);
        });
        parent.setSubMenuBases(subMenuBases);
    }

}
