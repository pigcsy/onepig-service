package com.one.pig.modular.system.controller;

import com.github.pagehelper.PageHelper;
import com.one.pig.common.annotion.Permission;
import com.one.pig.common.annotion.log.BussinessLog;
import com.one.pig.common.constant.Const;
import com.one.pig.common.constant.state.BizLogType;
import com.one.pig.common.controller.BaseController;
import com.one.pig.common.page.PageReq;
import com.one.pig.common.persistence.dao.LoginLogMapper;
import com.one.pig.common.persistence.dao.OperationLogMapper;
import com.one.pig.common.persistence.model.OperationLog;
import com.one.pig.core.support.BeanKit;
import com.one.pig.modular.system.warpper.LogWarpper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 日志管理的控制器
 *
 * @author fengshuonan
 * @Date 2017年4月5日 19:45:36
 */
@Controller
@RequestMapping("/log")
public class LogController extends BaseController {

    private static String PREFIX = "/system/log/";

    @Resource
    private OperationLogMapper operationLogMapper;

    @Resource
    private LoginLogMapper loginLogMapper;

    /**
     * 跳转到日志管理的首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "log.html";
    }

    /**
     * 查询操作日志列表
     */
    @RequestMapping("/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(@RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) String logName, @RequestParam(required = false) Integer logType) {
        PageReq params = defaultPage();
        PageHelper.offsetPage(params.getOffset(), params.getLimit());
        List<Map<String, Object>> result = loginLogMapper.getOperationLogs(beginTime, endTime, logName, BizLogType.valueOf(logType), params.getSort(), params.isAsc());
        return packForBT(result);
    }

    /**
     * 查询操作日志详情
     */
    @RequestMapping("/detail/{id}")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object detail(@PathVariable Integer id) {
        OperationLog operationLog = operationLogMapper.selectByPrimaryKey(id);
        Map<String, Object> stringObjectMap = BeanKit.beanToMap(operationLog);
        return super.warpObject(new LogWarpper(stringObjectMap));
    }

    /**
     * 清空日志
     */
    @BussinessLog(value = "清空业务日志")
    @RequestMapping("/delLog")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object delLog() {
        operationLogMapper.delete(new OperationLog());
        return super.SUCCESS_TIP;
    }
}
