package com.anniu.ordercall.bean.vo.report;

import java.util.List;

/**
 * 分页基类
 * -- 结果对象
 * @ClassName: PagingResult
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
public class PagingResult<Entity> {

    /**
     * 当前页码
     */
    private Integer page;

    /**
     * 每页条数
     */
    private Integer size;

    /**
     * 总记录数
     */
    private Integer totalCount;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 数据信息
     */
    private List<Entity> data;

    public PagingResult(List<Entity> data, int totalRecord, int size, Integer page) {
        int totalPage = (totalRecord % size) > 0 ? (totalRecord / size + 1) : (totalRecord / size);
        this.page = (page > totalPage) ? totalPage : page;
        this.size = size;
        this.totalPage = totalPage;
        this.totalCount = totalRecord;
        this.data = data;
    }

    public PagingResult() {

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

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<Entity> getData() {
        return data;
    }

    public void setData(List<Entity> data) {
        this.data = data;
    }

}
