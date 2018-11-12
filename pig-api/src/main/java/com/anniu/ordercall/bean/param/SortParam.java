package com.anniu.ordercall.bean.param;

import com.anniu.ordercall.bean.annotation.Column;

import java.io.Serializable;

/**
 * 排序信息 Param
 * @ClassName: SortParam
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
public class SortParam implements Serializable {

    /**
     * 主键Id
     */
    @Column(msg = "主键Id不允许为空")
    private Integer Id;

    /**
     * 排序权重
     */
    @Column(msg = "排序权重不允许为空")
    private Double sortPriority;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Double getSortPriority() {
        return sortPriority;
    }

    public void setSortPriority(Double sortPriority) {
        this.sortPriority = sortPriority;
    }

}
