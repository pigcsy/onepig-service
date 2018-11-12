package com.anniu.ordercall.bean.vo.report;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户信息 Vo
 * -- 客服信息
 * @ClassName: UserVo
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
@Data
@NoArgsConstructor
public class UserVo implements Serializable {

    /**
     * 主键Id
     */
    private Integer id;

    /**
     * 用户角色
     * -- 1 管理.
     * -- 2 客服.
     */
    private Integer role;

    /**
     * 用户姓名
     * -- 客服
     */
    private String userName;

}
