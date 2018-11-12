package com.anniu.ordercall.controller.common;

import com.anniu.ordercall.service.common.TaskService;
import com.anniu.ordercall.service.report.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务
 */
@Component
public class Task {

    @Autowired
    TaskService taskService;

    /**
     * 注入报告信息 Service, 赋值使用时别名为 reportService;
     */
    @Autowired
    private ReportService reportService;


    @Scheduled(cron = "0 0,1 0/1 * * ? ")
    // @Scheduled(cron = "0/3 * * * * ?")
    public void effectivePhone() {
        taskService.getOneOrderInfo();
    }

    /**
     * 刷新数据
     * -- 实时数据
     */
    @Scheduled(cron = "0 05 6/1 * * ?")
    public void refreshRealTime() {
        reportService.refreshRealTime();
    }

    /**
     * 汇总数据
     * -- 全局数据（ 管理:客服 ）
     * -- 历史数据（ 管理:客服 ）
     */
    @Scheduled(cron = "0 10 1 * * ?")
    public void collectHistory() {
        reportService.collectHistory();
    }

}
