package com.anniu.ordercall.model.ordercall;

import com.anniu.ordercall.core.base.BaseModel;
import lombok.Data;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author csy
 * @since 2017-07-11
 */
@Data
public class Menu extends BaseModel {

    private static final long serialVersionUID = 1L;
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


    @Override
    public String toString() {
        return "Menu{" +
                "userId=" + id +
                ", code=" + code +
                ", pcode=" + pcode +
                ", name=" + name +
                ", icon=" + icon +
                ", url=" + url +
                ", num=" + num +
                ", levels=" + levels +
                ", ismenu=" + ismenu +
                ", tips=" + tips +
                ", status=" + status +
                "}";
    }
}
