package com.anniu.ordercall.service.report.Impl;

import com.anniu.mid.freework.container.spring.web.ResponseEntity;
import com.anniu.ordercall.bean.param.ConstantParam;
import com.anniu.ordercall.bean.param.report.ObtainParam;
import com.anniu.ordercall.bean.param.report.ObtainParamP;
import com.anniu.ordercall.bean.vo.report.GroupVo;
import com.anniu.ordercall.service.report.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 报告信息 ServiceImpl
 * @ClassName: ReportServiceImpl
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
@Service
public class ReportServiceImpl extends ReportBaseService implements ReportService {


    /**
     * 创建日志工具
     */
    private static Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);


    /**
     * 获取信息
     * -- 实时数据
     * @return 处理结果
     */
    public ResponseEntity obtainRealTime(ObtainParam param) {
        verifyMainKey(param.getUserId());
        return param.getRole().equals(ConstantParam.ConstantOne) ? obtainRealTimeM(param) : obtainRealTimeC(param);
    }

    /**
     * 获取信息
     * -- 汇总数据
     * @return 处理结果
     */
    public ResponseEntity obtainCollect(ObtainParam param) {
        verifyMainKey(param.getUserId());
        return param.getRole().equals(ConstantParam.ConstantOne) ? obtainCollectM(param) : obtainCollectC(param);
    }

    /**
     * 获取信息
     * -- 历史数据
     * @param param 获取参数
     * @return 处理结果
     */
    public ResponseEntity obtainHistory(ObtainParamP param) {
        verifyMainKey(param.getUserId());
        return param.getRole().equals(ConstantParam.ConstantOne) ? obtainHistoryM(param) : obtainHistoryC(param);
    }

    /**
     * 获取信息
     * -- 客服信息
     * @return 处理结果
     */
    public ResponseEntity obtainCustomer() {
       return ResponseEntity.success(reportMapper.obtainCustomer());
    }


    /**
     * 刷新数据
     * -- 实时数据
     */
    public void refreshRealTime() {
        String time = obtainDateTime();
        log.info(time.concat(" 数据刷新开始处理... "));
        // 获取数据（ 实时 ）
        Map<Integer, List<GroupVo>> mapSet = obtainRealTime();
        // 实时数据（ 客服 ）
        refreshRealTimeC(mapSet);
        // 实时数据（ 管理 ）
        refreshRealTimeM(mapSet);
        log.info(time.concat(" 数据刷新处理完成. "));
    }

    /**
     * 汇总数据
     * -- 全局数据（ 管理:客服 ）
     * -- 历史数据（ 管理:客服 ）
     */
    public void collectHistory() {
        String time = obtainDateTime();
        log.info(time.concat(" 数据统计开始处理... "));
        // 客服汇总（ 每日 ）
        collectHistoryC();
        // 管理汇总（ 每日 ）
        collectHistoryM();
        // 客服汇总（ 全局 ）
        collectGlobalC();
        // 管理汇总（ 全局 ）
        collectGlobalM();
        log.info(time.concat(" 数据统计处理完成. "));
    }
}
