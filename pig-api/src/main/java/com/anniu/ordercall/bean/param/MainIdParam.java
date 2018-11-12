package com.anniu.ordercall.bean.param;

import com.anniu.ordercall.bean.annotation.Column;

import java.io.Serializable;

/**
 * 主键Id信息 Param
 * @ClassName: MainIdParam
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
public class MainIdParam implements Serializable {

    /**
     * 主键Id
     */
    @Column(msg = "主键Id不允许为空")
    private Integer Id;

    public MainIdParam(Integer id) {
        Id = id;
    }

    public MainIdParam() {

    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

}
