package com.one.pig.core.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuBase implements Comparable<MenuBase>, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer id;

    private List<MenuBase> subMenuBases;
    /**
     * 菜单编号
     */
    private String code;
    /**
     * 菜单父编号
     */
    private String pcode;

    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * url地址
     */
    private String url;
    /**
     * 菜单排序号
     */
    private Integer num;
    /**
     * 菜单层级
     */
    private Integer levels;
    /**
     * 是否是菜单（1：是  0：不是）
     */
    private Integer ismenu;
    /**
     * 备注
     */
    private String tips;
    /**
     * 菜单状态 :  1:启用   0:不启用
     */
    private Integer status;

    public MenuBase(Integer id, String url, String code, Integer num, String pcode, String icon, Integer ismenu, String name, Integer status, String tips, Integer levels) {
        this.id = id;
        this.url = url;
        this.code = code;
        this.num = num;
        this.pcode = pcode;
        this.ismenu = ismenu;
        this.name = name;
        this.icon = icon;
        this.status = status;
        this.tips = tips;
        this.levels = levels;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuBase menuBase = (MenuBase) o;

        return getId() == menuBase.getId();

    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public int compareTo(MenuBase o) {
        if (this.getNum() > o.getNum()) {
            return 1;
        } else {
            return -1;
        }
    }
}
