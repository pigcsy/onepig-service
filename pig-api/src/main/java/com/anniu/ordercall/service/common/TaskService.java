package com.anniu.ordercall.service.common;


public interface TaskService {
    /**
     * 每小时获取一次当天用户完成订单数和付款金额
     */
    void getOneOrderInfo();
}
