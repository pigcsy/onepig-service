package com.anniu.ordercall.service.report;

import com.anniu.mid.freework.container.spring.web.ResponseEntity;
import com.anniu.ordercall.bean.param.report.ObtainParam;
import com.anniu.ordercall.bean.param.report.ObtainParamP;

/**
 * 报告信息 Service
 * @ClassName: ReportService
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
public interface ReportService {


    /**
     * 获取信息
     * -- 实时数据
     * @return 处理结果
     */
    ResponseEntity obtainRealTime(ObtainParam param);

    /**
     * 获取信息
     * -- 汇总数据
     * @return 处理结果
     */
    ResponseEntity obtainCollect(ObtainParam param);

    /**
     * 获取信息
     * -- 历史数据
     * @param param 获取参数
     * @return 处理结果
     */
    ResponseEntity obtainHistory(ObtainParamP param);

    /**
     * 获取信息
     * -- 客服信息
     * @return 处理结果
     */
    ResponseEntity obtainCustomer();


    /**
     * 刷新数据
     * -- 实时数据
     */
    void refreshRealTime();

    /**
     * 汇总数据
     * -- 全局数据（ 管理:客服 ）
     * -- 历史数据（ 管理:客服 ）
     */
    void collectHistory();

}
