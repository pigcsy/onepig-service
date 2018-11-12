package com.anniu.ordercall.model.ordercall;

import com.anniu.ordercall.core.base.BaseModel;

import java.util.Date;

public class IpAllow extends BaseModel {

    private String ip;

    private Date createTime;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}