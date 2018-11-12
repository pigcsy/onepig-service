package com.anniu.ordercall.model.ordercall;

import com.anniu.ordercall.core.base.BaseModel;
import lombok.Data;

/**
 * <p>
 * 角色和菜单关联表
 * </p>
 *
 * @author daze
 * @since 2017-07-11
 */
@Data
public class Relation extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    private Integer menuId;
    /**
     * 角色id
     */
    private Integer roleId;

    @Override
    public String toString() {
        return "Relation{" +
                "userId=" + id +
                ", menuId=" + menuId +
                ", roleId=" + roleId +
                "}";
    }
}
