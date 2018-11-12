package com.anniu.ordercall.bean.param;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 排序 Param
 * @ClassName: SortingParam
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
public class SortingParam implements Serializable {

    /**
     * 属性名称
     */
    private String propertyName;

    /**
     * 排序方式
     */
    private String sortingManner;

    public SortingParam(String propertyName, String sortingManner) {
        this.propertyName = propertyName;
        this.sortingManner = sortingManner;
    }

    public SortingParam() {

    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getSortingManner() {
        return sortingManner;
    }

    public void setSortingManner(String sortingManner) {
        this.sortingManner = sortingManner;
    }

    public List<SortingParam> obtainSortingParam(String propertyName, String sortingManner) {
        List<SortingParam> list = new ArrayList();
        list.add(new SortingParam(propertyName, sortingManner));
        return list;
    }

}
