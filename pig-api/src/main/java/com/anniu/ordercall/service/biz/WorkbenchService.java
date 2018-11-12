package com.anniu.ordercall.service.biz;


import com.anniu.ordercall.bean.vo.biz.CallOrderVo;
import com.anniu.ordercall.model.moneyarrive.MoneyarriveOrder;
import com.anniu.ordercall.model.ordercall.CallOrder;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface WorkbenchService {
	
    List<MoneyarriveOrder> getOrder(Integer userId);
    
    void submitOrder(String callResult, String dealResult, Integer callOrderId);
    
    PageInfo<CallOrderVo> historyRecord(Integer pageNum, Integer pageSize, Integer userId);
    
    List<Integer> vDayReports(Integer serviceId);
    
    PageInfo<CallOrder> vDayRecord(Integer pageNum, Integer pageSize, Integer userId);
    
    List<Integer> refundRatio(Integer serviceId);
    
    PageInfo<CallOrder> searchOrderByPhone(Integer pageNum, Integer pageSize, String userPhone);
    
    Integer updateEntity(String orderId,Integer payStatus);
}
