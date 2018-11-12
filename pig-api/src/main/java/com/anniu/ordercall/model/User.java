package com.anniu.ordercall.model.ordercall;

import com.anniu.ordercall.core.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author daze
 * @since 2017-07-11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * md5密码盐
     */
    private String salt;
    /**
     * 名字
     */
    private String name;

    /**
     * 角色id
     */
    private String roleId;
    /**
     * 部门id
     */
    private Integer deptId;
    /**
     * 状态(1：启用  2：冻结  3：删除）
     */
    private Integer status;

    private Date createDate;

    private Date updateDate;


    public void setAccount(String account) {
        this.account = account;
    }
}
