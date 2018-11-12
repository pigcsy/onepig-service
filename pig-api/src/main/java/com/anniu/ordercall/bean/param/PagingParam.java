package com.anniu.ordercall.bean.param;

import java.io.Serializable;
import java.util.List;

/**
 * 分页 Param
 * @ClassName: PagingParam
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
public class PagingParam implements Serializable {

    /**
     * 页码
     */
    private Integer page;

    /**
     * 条数
     */
    private Integer size;

    /**
     * 排序
     */
    private List<SortingParam> sortingParam;

    public PagingParam(List<SortingParam> sortingParam) {
        this.sortingParam = sortingParam;
    }

    public PagingParam() {

    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<SortingParam> getSortingParam() {
        return sortingParam;
    }

    public void setSortingParam(List<SortingParam> sortingParam) {
        this.sortingParam = sortingParam;
    }

    public PagingParam setParam(List<SortingParam> sortingParam) {
        this.sortingParam = sortingParam;
        return this;
    }

}
