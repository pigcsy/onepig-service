package com.anniu.ordercall.repository.ordercall;


import com.anniu.ordercall.bean.vo.biz.CallOrderVo;
import com.anniu.ordercall.model.ordercall.CallOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository
public interface CallOrderMapper extends Mapper<CallOrder> {

    List<Integer> selectOutUser(@Param("oneDayAgo") Date oneDayAgo, @Param("userIdSet") Set<Integer> userIdSet);

    List<CallOrder> selectTodayData(@Param("todayZeroTime") Date todayZeroTime, @Param("nowTime") Date nowTime);

    List<CallOrder> selectByServiceId(@Param("serviceId") Integer serviceId);

    List<CallOrderVo> historyRecord(@Param("serviceId") Integer serviceId);
    
    List<Integer> vDayReports(@Param("serviceId")Integer serviceId,@Param("param")String param);
    
    List<CallOrder> vDayRecord(@Param("param") String param);
    
    List<Integer> refundRatio(@Param("serviceId")Integer serviceId,@Param("yesterday")String yesterday);
    
    List<CallOrder> searchOrderByPhone(@Param("userPhone")String userPhone);
    
    Integer updateEntity(@Param("orderId")String orderId,@Param("payStatus")Integer payStatus);
}