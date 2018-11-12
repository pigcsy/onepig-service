package com.anniu.ordercall.bean.param.report;

import com.anniu.ordercall.bean.annotation.Column;
import com.anniu.ordercall.bean.param.PagingParam;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取信息 Param
 * -- 历史数据
 * @ClassName: ObtainParamP
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
@Data
@NoArgsConstructor
public class ObtainParamP extends PagingParam {

    /**
     * 登录角色
     * -- 用户角色
     * -- 1 管理.
     * -- 2 客服.
     */
    @Column(msg = "登录角色不允许为空")
    private Integer role;

    /**
     * 登录用户
     * -- 用户主键(客服)
     */
    @Column(msg = "登录用户不允许为空")
    private Integer userId;

    public ObtainParamP setParam(Integer role, Integer userId) {
        this.role = role;
        this.userId = userId;
        return this;
    }

}
