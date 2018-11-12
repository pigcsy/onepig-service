package com.anniu.ordercall.core.enums;

/**
 * 数据库排序
 *
 * @author csy
 */
public enum OrderEnum {

    ASC("asc"), DESC("desc");

    private String des;


    OrderEnum(String des) {
        this.des = des;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
