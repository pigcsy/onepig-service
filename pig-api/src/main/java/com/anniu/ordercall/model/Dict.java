package com.anniu.ordercall.model.ordercall;

import com.anniu.ordercall.core.base.BaseModel;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author csy
 * @since 2017-07-11
 */
public class Dict extends BaseModel {

    private static final long serialVersionUID = 1L;
    /**
     * 排序
     */
    private Integer num;
    /**
     * 父级字典
     */
    private Integer pid;
    /**
     * 名称
     */
    private String name;
    /**
     * 提示
     */
    private String tips;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    @Override
    public String toString() {
        return "Dict{" +
                "userId=" + id +
                ", num=" + num +
                ", pid=" + pid +
                ", name=" + name +
                ", tips=" + tips +
                "}";
    }
}
