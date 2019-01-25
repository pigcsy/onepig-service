package com.one.pig.token;

import lombok.Data;

import java.io.Serializable;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息
 *
 * @author csy
 * @date 2016年12月5日 上午10:26:43
 */
@Data
public class ShiroUser implements Serializable {

    private static final long serialVersionUID = 1L;

    public Long userId;          // 主键ID
    public String userPhone;      // 账号
    public Integer role;      // 系统
    public boolean isWhiteUser;      // 系统


    /**
     * 范围
     * -- 生效范围( 1 : 征信查询 )
     */
    private Integer scope = 1;


}
