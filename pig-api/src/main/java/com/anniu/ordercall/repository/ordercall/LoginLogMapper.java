package com.anniu.ordercall.repository.ordercall;

import com.anniu.ordercall.model.ordercall.LoginLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 登录记录 Mapper 接口
 * </p>
 *
 * @author csy
 * @since 2017-07-11
 */
@Repository
public interface LoginLogMapper extends Mapper<LoginLog> {

    /**
     * 获取操作日志
     *
     * @author csy
     * @Date 2017/4/16 23:48
     */
    List<Map<String, Object>> getOperationLogs(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("logName") String logName, @Param("logType") String logType, @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);

    /**
     * 获取登录日志
     *
     * @author csy
     * @Date 2017/4/16 23:48
     */
    List<Map<String, Object>> getLoginLogs(@Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("logName") String logName, @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);

}