package com.anniu.ordercall.bean.param;

/**
 * 查询SQL Param
 * @ClassName: SQLParam
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
public class SQLParam {

    /**
     * 数据SQL
     */
    private String dataSQL;

    /**
     * 统计SQL
     */
    private String countSQL;

    public SQLParam(String dataSQL, String countSQL) {
        this.dataSQL = dataSQL;
        this.countSQL = countSQL;
    }

    public SQLParam(String dataSQL) {
        this.dataSQL = dataSQL;
    }

    public String getDataSQL() {
        return dataSQL;
    }

    public void setDataSQL(String dataSQL) {
        this.dataSQL = dataSQL;
    }

    public String getCountSQL() {
        return countSQL;
    }

    public void setCountSQL(String countSQL) {
        this.countSQL = countSQL;
    }

}
