package com.anniu.ordercall.controller.report;

import com.anniu.mid.freework.container.spring.web.ResponseEntity;
import com.anniu.ordercall.bean.dto.user.UserDto;
import com.anniu.ordercall.bean.enums.ResultEnum;
import com.anniu.ordercall.bean.param.ConstantParam;
import com.anniu.ordercall.bean.param.report.ObtainParam;
import com.anniu.ordercall.bean.param.report.ObtainParamP;
import com.anniu.ordercall.bean.tool.OpenException;
import com.anniu.ordercall.bean.tool.VerifyTool;
import com.anniu.ordercall.service.report.ReportService;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 报告信息 Controller
 * @ClassName: ReportController
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
@RestController
@RequestMapping(value = "/report", method = {RequestMethod.POST})
@CrossOrigin
public class ReportController {


    /**
     * 注入报告信息 Service, 赋值使用时别名为 reportService;
     */
    @Autowired
    private ReportService reportService;


    /**
     * 获取信息
     * -- 实时数据
     * @return 处理结果
     */
    @RequiresUser
    @RequestMapping(value = "/obtainRealTime")
    public Object obtainRealTime(UserDto user) {
        if (user == null || user.getUserId() == null || user.getRoleId() == null)
            throw new OpenException("登录超时 , 请重新登录", ResultEnum.ServerError);
        return reportService.obtainRealTime(new ObtainParam(Integer.valueOf(user.getRoleId()), user.getUserId()));
    }

    /**
     * 获取信息
     * -- 实时数据
     * -- 根据客服
     * @return 处理结果
     */
    @RequiresUser
    @RequestMapping(value = "/obtainRealTimeM")
    public Object obtainRealTimeM(@RequestBody ObtainParam param, UserDto user) {
        if (user == null || user.getUserId() == null || user.getRoleId() == null)
            throw new OpenException("登录超时 , 请重新登录", ResultEnum.ServerError);
        if (!user.getRoleId().equals(String.valueOf(ConstantParam.ConstantOne)))
            throw new OpenException("权限不足 , 需要管理员权限", ResultEnum.ServerError);
        if (param != null)
            param.setRole(ConstantParam.ConstantTwo);
        VerifyTool.verifyParam(param);
        return reportService.obtainRealTime(param);
    }

    /**
     * 获取信息
     * -- 汇总数据
     * @return 处理结果
     */
    @RequiresUser
    @RequestMapping(value = "/obtainCollect")
    public Object obtainCollect(UserDto user) {
        if (user == null || user.getUserId() == null || user.getRoleId() == null)
            throw new OpenException("登录超时 , 请重新登录", ResultEnum.ServerError);
        return reportService.obtainCollect(new ObtainParam(Integer.valueOf(user.getRoleId()), user.getUserId()));
    }

    /**
     * 获取信息
     * -- 汇总数据
     * -- 根据客服
     * @return 处理结果
     */
    @RequiresUser
    @RequestMapping(value = "/obtainCollectM")
    public Object obtainCollectM(@RequestBody ObtainParam param, UserDto user) {
        if (user == null || user.getUserId() == null || user.getRoleId() == null)
            throw new OpenException("登录超时 , 请重新登录", ResultEnum.ServerError);
        if (!user.getRoleId().equals(String.valueOf(ConstantParam.ConstantOne)))
            throw new OpenException("权限不足 , 需要管理员权限", ResultEnum.ServerError);
        if (param != null)
            param.setRole(ConstantParam.ConstantTwo);
        VerifyTool.verifyParam(param);
        return reportService.obtainCollect(param);
    }

    /**
     * 获取信息
     * -- 历史数据
     * @param param 获取参数
     * @return 处理结果
     */
    @RequiresUser
    @RequestMapping(value = "/obtainHistory")
    public Object obtainHistory(@RequestBody ObtainParamP param, UserDto user) {
        if (user == null || user.getUserId() == null || user.getRoleId() == null)
            throw new OpenException("登录超时 , 请重新登录", ResultEnum.ServerError);
        param.setParam(Integer.valueOf(user.getRoleId()), user.getUserId());
        return reportService.obtainHistory(param);
    }

    /**
     * 获取信息
     * -- 历史数据
     * -- 根据客服
     * @param param 获取参数
     * @return 处理结果
     */
    @RequiresUser
    @RequestMapping(value = "/obtainHistoryM")
    public Object obtainHistoryM(@RequestBody ObtainParamP param, UserDto user) {
        if (user == null || user.getUserId() == null || user.getRoleId() == null)
            throw new OpenException("登录超时 , 请重新登录", ResultEnum.ServerError);
        if (!user.getRoleId().equals(String.valueOf(ConstantParam.ConstantOne)))
            throw new OpenException("权限不足 , 需要管理员权限", ResultEnum.ServerError);
        if (param != null)
            param.setRole(ConstantParam.ConstantTwo);
        VerifyTool.verifyParam(param);
        return reportService.obtainHistory(param);
    }

    /**
     * 获取信息
     * -- 客服信息
     * @return 处理结果
     */
    @RequiresUser
    @RequestMapping(value = "/obtainCustomer")
    public Object obtainCustomer(UserDto user) {
        if (user == null || user.getUserId() == null || user.getRoleId() == null)
            throw new OpenException("登录超时 , 请重新登录", ResultEnum.ServerError);
        if (!user.getRoleId().equals(String.valueOf(ConstantParam.ConstantOne)))
            throw new OpenException("权限不足 , 需要管理员权限", ResultEnum.ServerError);
        return reportService.obtainCustomer();
    }


    /**
     * 刷新数据
     * -- 实时数据
     */
    @RequestMapping(value="/refreshRealTime")
    public Object refreshRealTime() {
        reportService.refreshRealTime();
        return ResponseEntity.success();
    }

    /**
     * 汇总数据
     * -- 全局数据（ 管理:客服 ）
     * -- 历史数据（ 管理:客服 ）
     */
    @RequestMapping(value="/collectHistory")
    public Object collectHistory() {
        reportService.collectHistory();
        return ResponseEntity.success();
    }
}
