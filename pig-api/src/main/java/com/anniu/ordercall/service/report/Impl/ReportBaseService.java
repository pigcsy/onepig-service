package com.anniu.ordercall.service.report.Impl;

import com.alibaba.fastjson.JSONObject;
import com.anniu.mid.freework.container.spring.web.ResponseEntity;
import com.anniu.ordercall.bean.dto.report.GlobalDto;
import com.anniu.ordercall.bean.enums.ResultEnum;
import com.anniu.ordercall.bean.param.ConstantParam;
import com.anniu.ordercall.bean.param.PagingParam;
import com.anniu.ordercall.bean.param.SQLParam;
import com.anniu.ordercall.bean.param.SortingParam;
import com.anniu.ordercall.bean.param.report.ObtainParam;
import com.anniu.ordercall.bean.param.report.ObtainParamP;
import com.anniu.ordercall.bean.tool.OpenException;
import com.anniu.ordercall.bean.tool.VerifyTool;
import com.anniu.ordercall.bean.vo.report.*;
import com.anniu.ordercall.core.utils.DateUtils;
import com.anniu.ordercall.model.ordercall.CallOrder;
import com.anniu.ordercall.model.ordercall.Collect;
import com.anniu.ordercall.model.ordercall.GlobalCollect;
import com.anniu.ordercall.model.ordercall.Report;
import com.anniu.ordercall.repository.moneyarrive.MoneyarriveOrderMapper;
import com.anniu.ordercall.repository.ordercall.CallOrderMapper;
import com.anniu.ordercall.repository.ordercall.CollectMapper;
import com.anniu.ordercall.repository.ordercall.ConfigMapper;
import com.anniu.ordercall.repository.ordercall.GlobalCollectMapper;
import com.anniu.ordercall.repository.ordercall.ReportMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.anniu.ordercall.core.utils.DateUtils.YYYY_MM_DD_HH_MM_SS;

/**
 * 基础 Service
 * @ClassName: ReportBaseService
 * @DetaTime 2018-03-13 09:48:32
 * @author 花花
 */
@Service
public class ReportBaseService {


    /**
     * 初始化日期转换工具
     */
    private static final SimpleDateFormat dateFormatToSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateFormatToDay = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat dateFormatToDayByNoBar = new SimpleDateFormat("yyyyMMdd");

    /**
     * 创建日志工具
     */
    private static Logger log = LoggerFactory.getLogger(ReportBaseService.class);

    /**
     * 注入全局汇总 Mapper, 赋值使用时别名为 globalMapper;
     */
    @Autowired
    protected GlobalCollectMapper globalMapper;

    /**
     * 注入汇总信息 Mapper, 赋值使用时别名为 collectMapper;
     */
    @Autowired
    protected CollectMapper collectMapper;

    /**
     * 注入报告信息 Mapper, 赋值使用时别名为 reportMapper;
     */
    @Autowired
    protected ReportMapper reportMapper;

    /**
     * 注入配置信息 Mapper, 赋值使用时别名为 configMapper;
     */
    @Autowired
    protected ConfigMapper configMapper;

    /**
     * 注入订单 Mapper, 赋值使用时别名为 callOrderMapper;
     */
    @Autowired
    CallOrderMapper callOrderMapper;
    
    /**
     * 注入订单 Mapper, 赋值使用时别名为 moneyArriveOrderMapper;
     */
    @Autowired
    protected MoneyarriveOrderMapper moneyArriveOrderMapper;
    
    /**
     * 注入 Redis 管理类, 赋值使用时别名为 redisOperations;
     */
    @Autowired
    RedisOperations redisOperations;


    /**
     * 获取Sql
     * -- Sort 排序Sql
     * @param param 获取参数
     * @return 处理结果
     */
    private String obtainSortSql(List<SortingParam> param) {
        StringBuffer sql = new StringBuffer("");
        if(param != null && !param.isEmpty() && param.size() > ConstantParam.ConstantZero)
            sql.append(VerifyTool.processSortingParam(param));
        return sql.toString();
    }

    /**
     * 获取Sql
     * -- Paging 分页Sql
     * @param param 获取参数
     * @param sql SQL参数
     * @return 处理结果
     */
    protected SQLParam obtainPagingSql(PagingParam param, StringBuffer sql) {
        String countSQL = sql.toString();
        sql.append(obtainSortSql(param.getSortingParam()));
        SQLParam sqlParam = new SQLParam(sql.toString());
        if((param.getPage() != null && param.getPage() >= ConstantParam.ConstantOne) && (param.getSize() != null && param.getSize() >= ConstantParam.ConstantOne)) {
            sql.append(" limit ").append((param.getPage() - ConstantParam.ConstantOne) * param.getSize() + ",").append(param.getSize());
            sqlParam.setDataSQL(sql.toString());
            sqlParam.setCountSQL(countSQL);
        }
        return sqlParam;
    }


    /**
     * 获取信息
     * -- 实时数据
     * -- 管理
     * @return 处理结果
     */
    protected ResponseEntity obtainRealTimeM(ObtainParam param) {
        RealTimeVoM vo = new RealTimeVoM();
        Object item = redisOperations.opsForValue().get(ConstantParam.ManageRedisKey.concat(String.valueOf(ConstantParam.ConstantZero)));
        if(item != null)
            vo = JSONObject.parseObject(item.toString(), RealTimeVoM.class);
       return ResponseEntity.success(vo);
    }

    /**
     * 获取信息
     * -- 实时数据
     * -- 客服
     * @return 处理结果
     */
    protected ResponseEntity obtainRealTimeC(ObtainParam param) {
        RealTimeVoC vo = new RealTimeVoC();
        Object item = redisOperations.opsForValue().get(ConstantParam.CustomerRedisKey.concat(String.valueOf(param.getUserId())));
        if(item != null)
            vo = JSONObject.parseObject(item.toString(), RealTimeVoC.class);
        return ResponseEntity.success(vo);
    }

    /**
     * 获取信息
     * -- 汇总数据
     * -- 管理
     * @return 处理结果
     */
    protected ResponseEntity obtainCollectM(ObtainParam param) {
        CollectVoM vo = new CollectVoM();
        List<GlobalCollect> collectSet = globalMapper.obtainEntity("scope = 0");
        if(collectSet != null && !collectSet.isEmpty() && collectSet.size() > ConstantParam.ConstantZero)
            vo = convertDataModel(collectSet.get(ConstantParam.ConstantZero), CollectVoM.class);
        return ResponseEntity.success(vo);
    }

    /**
     * 获取信息
     * -- 汇总数据
     * -- 客服
     * @return 处理结果
     */
    protected ResponseEntity obtainCollectC(ObtainParam param) {
        CollectVoC vo = new CollectVoC();
        List<GlobalCollect> collectSet = globalMapper.obtainEntity("scope = 1 and userId = " + param.getUserId());
        if(collectSet != null && !collectSet.isEmpty() && collectSet.size() > ConstantParam.ConstantZero)
            vo = convertDataModel(collectSet.get(ConstantParam.ConstantZero), CollectVoC.class);
        return ResponseEntity.success(vo);
    }

    /**
     * 获取信息
     * -- 历史数据
     * -- 管理
     * @param param 获取参数
     * @return 处理结果
     */
    protected ResponseEntity obtainHistoryM(ObtainParamP param) {
        VerifyTool.verifyPagingParam(param, HistoryVoM.class, new SortingParam("createTime", ConstantParam.SortingMannerDesc));
        SQLParam sqlParam = obtainPagingSql(param, new StringBuffer("1 = 1"));
        return ResponseEntity.success(new PagingResult(convertDataModel(collectMapper.obtainEntityByPage(sqlParam.getDataSQL()), HistoryVoM.class), collectMapper.obtainEntityCount(sqlParam.getCountSQL()), param.getSize(), param.getPage()));
    }

    /**
     * 获取信息
     * -- 历史数据
     * -- 客服
     * @param param 获取参数
     * @return 处理结果
     */
    protected ResponseEntity obtainHistoryC(ObtainParamP param) {
        VerifyTool.verifyPagingParam(param, HistoryVoC.class, new SortingParam("createTime", ConstantParam.SortingMannerDesc));
        SQLParam sqlParam = obtainPagingSql(param, new StringBuffer("1 = 1 and userId = ").append(param.getUserId()));
        return ResponseEntity.success(new PagingResult(convertDataModel(reportMapper.obtainEntityByPage(sqlParam.getDataSQL()), HistoryVoC.class), reportMapper.obtainEntityCount(sqlParam.getCountSQL()), param.getSize(), param.getPage()));
    }


    /**
     * 获取数据
     * -- 实时数据
     */
    protected Map<Integer, List<GroupVo>> obtainRealTime() {
        String time = obtainDateTime();
        log.info(time.concat(" 获取实时数据开始... "));
        List<RedisVo> collection = new ArrayList<>();
//        Object redisSet = redisOperations.opsForValue().get(XYZC_ORDER_INFO);
//        if (redisSet != null)
//            collection = JSONObject.parseArray(redisSet.toString(), RedisVo.class);
        Date todayZeroTime = DateUtils.setDateByStr(DateUtils.getDay() + " 00:00:00", YYYY_MM_DD_HH_MM_SS);
        List<CallOrder> callOrderList = callOrderMapper.selectTodayData(todayZeroTime, new Date());
        StringBuilder sql=new StringBuilder("'");
        for (CallOrder callOrder : callOrderList) {
        	sql.append(callOrder.getUserPhone()).append("','");
		}
        sql.append("'");
        collection=moneyArriveOrderMapper.getPayOrder(sql.toString());
        Map<Integer, List<GroupVo>> mapSet = new HashMap<>();
        for (RedisVo vo : collection) {
            List<GroupVo> itemSet = mapSet.get(vo.getServiceId());
            if(itemSet == null) {
                itemSet = new ArrayList<>();
                itemSet.add(convertDataModel(vo, GroupVo.class));
                mapSet.put(vo.getServiceId(), itemSet);
            } else
                itemSet.add(convertDataModel(vo, GroupVo.class));
        }
        log.info(time.concat(" 获取实时数据完成. "));
        return mapSet;
    }

    /**
     * 刷新数据
     * -- 实时数据
     * -- 管理
     */
    protected void refreshRealTimeM(Map<Integer, List<GroupVo>> mapSet) {
        String time = obtainTime(), dateTime = obtainDateTime();
        log.info(dateTime.concat("（ 管理 ）数据刷新开始处理... "));
        StringBuffer sql = new StringBuffer("1 = 1").append(" and begin_time >= DATE_FORMAT('" + obtainStartTime(time) + "','%Y-%m-%d %H:%i:%s')").append(" and begin_time <= DATE_FORMAT('" + obtainEndTime(time) + "','%Y-%m-%d %H:%i:%s')");
        Collect collect = reportMapper.collectRealTimeM(sql.toString());
        if(collect != null && collect.getHandleAmount() > ConstantParam.ConstantZero && collect.getCustomerAmount() > ConstantParam.ConstantZero) {
            for (Integer key : mapSet.keySet()) {
                List<GroupVo> voSet = mapSet.get(key);
                if(voSet != null) {
                    for (GroupVo vo: voSet) {
                        collect.setPayAmount(add(collect.getPayAmount(), vo.getOrderNum()));
                        collect.setPaymentAmount(add(collect.getPaymentAmount(), vo.getOrderAmount()));
                    }
                    collect.setUserAmount(add(collect.getUserAmount(), voSet.size()));
                }
            }
            // 接通率
            collect.setConnectRate(divide(collect.getConnectAmount(), collect.getHandleAmount()));
            // 有效沟通率
            collect.setCommRate(ConstantParam.ConstantZeroPointZeroZero);
            if(collect.getConnectAmount() != null && collect.getConnectAmount() > ConstantParam.ConstantZero)
                collect.setCommRate(divide(collect.getCommAmount(), collect.getConnectAmount()));
            // 有效沟通率（ 全局 ）
            collect.setGlobalCommRate(divide(collect.getCommAmount(), collect.getHandleAmount()));
            // 用户转化率
            collect.setConvertRate(divide(collect.getUserAmount(), collect.getHandleAmount()));
            // 平均处理时间
            collect.setAverageTime(ConstantParam.ConstantZero);
            if(collect.getTotalTime() != null) {
                collect.setAverageTime(divideByInt(collect.getTotalTime(), collect.getHandleAmount()));
            } else
                collect.setTotalTime(ConstantParam.ConstantZero);
            // 平均处理时间（ 客服 ）
            collect.setCustomerTime(Double.valueOf(ConstantParam.ConstantZero));
            if(collect.getCustomerAmount() != null && collect.getCustomerAmount() > ConstantParam.ConstantZero)
                collect.setCustomerTime(divides(divideByInt(collect.getTotalTime(), collect.getCustomerAmount()), ConstantParam.ConstantSixty));
            // 平均处理量（ 客服 ）
            collect.setAverageAmount(ConstantParam.ConstantZero);
            if(collect.getCustomerAmount() != null && collect.getCustomerAmount() > ConstantParam.ConstantZero)
                collect.setAverageAmount(divideByInt(collect.getHandleAmount(), collect.getCustomerAmount()));
            collect.setModifyTime(new Date());
            redisOperations.opsForValue().set(ConstantParam.ManageRedisKey.concat(String.valueOf(ConstantParam.ConstantZero)), JSONObject.toJSONString(convertDataModel(collect, RealTimeVoM.class)), ConstantParam.RedisExpiredTime, TimeUnit.SECONDS);
        } else
            log.info(dateTime.concat("（ 管理 ）刷新实时数据失败, 未统计到实时数据... "));
        log.info(dateTime.concat("（ 管理 ）数据刷新处理完成. "));
    }

    /**
     * 刷新数据
     * -- 实时数据
     * -- 客服
     */
    protected void refreshRealTimeC(Map<Integer, List<GroupVo>> mapSet) {
        String time = obtainTime(), dateTime = obtainDateTime();
        log.info(dateTime.concat("（ 客服 ）数据刷新开始处理... "));
        StringBuffer sql = new StringBuffer("1 = 1").append(" and begin_time >= DATE_FORMAT('" + obtainStartTime(time) + "','%Y-%m-%d %H:%i:%s')").append(" and begin_time <= DATE_FORMAT('" + obtainEndTime(time) + "','%Y-%m-%d %H:%i:%s')");
        List<Report> reportSet = reportMapper.collectReportC(sql.toString());
        reportSet.stream().map(target -> {
            List<GroupVo> voSet = mapSet.get(target.getUserId());
            if(voSet != null) {
                for (GroupVo vo: voSet) {
                    target.setPayAmount(add(target.getPayAmount(), vo.getOrderNum()));
                    target.setPaymentAmount(add(target.getPaymentAmount(), vo.getOrderAmount()));
                }
                target.setUserAmount(add(target.getUserAmount(), voSet.size()));
            }
            // 接通率
            target.setConnectRate(divide(target.getConnectAmount(), target.getHandleAmount()));
            // 有效沟通率
            target.setCommRate(ConstantParam.ConstantZeroPointZeroZero);
            if(target.getConnectAmount() != null && target.getConnectAmount() > ConstantParam.ConstantZero)
                target.setCommRate(divide(target.getCommAmount(), target.getConnectAmount()));
            // 有效沟通率（ 全局 ）
            target.setGlobalCommRate(divide(target.getCommAmount(), target.getHandleAmount()));
            // 用户转化率
            target.setConvertRate(divide(target.getUserAmount(), target.getHandleAmount()));
            // 平均处理时间
            target.setAverageTime(ConstantParam.ConstantZero);
            if(target.getTotalTime() != null) {
                target.setAverageTime(divideByInt(target.getTotalTime(), target.getHandleAmount()));
            } else
                target.setTotalTime(ConstantParam.ConstantZero);
            target.setModifyTime(new Date());
            RealTimeVoC vo = convertDataModel(target, RealTimeVoC.class);
            // 累计处理时间（ 分 ）
            vo.setTotalDateTime(divides(vo.getTotalTime(), ConstantParam.ConstantSixty));
            redisOperations.opsForValue().set(ConstantParam.CustomerRedisKey.concat(String.valueOf(target.getUserId())), JSONObject.toJSONString(vo), ConstantParam.RedisExpiredTime, TimeUnit.SECONDS);
            return target;
        }).collect(Collectors.toList());
        log.info(dateTime.concat("（ 客服 ）数据刷新处理完成. "));
    }

    /**
     * 汇总数据
     * -- 历史数据
     * -- 管理
     */
    protected void collectHistoryM() {
        String time = obtainTime(), dateTime = obtainDateTime();
        log.info(dateTime.concat("（ 管理 ）历史数据统计开始处理... "));
        StringBuffer sql = new StringBuffer("1 = 1").append(" and createTime >= DATE_FORMAT('" + obtainStartTime(time) + "','%Y-%m-%d %H:%i:%s')").append(" and createTime <= DATE_FORMAT('" + obtainEndTime(time) + "','%Y-%m-%d %H:%i:%s')");
        Collect collect = reportMapper.collectReportM(sql.toString());
        if(collect != null && collect.getCustomerAmount() > ConstantParam.ConstantZero) {
            // 接通率
            collect.setConnectRate(divide(collect.getConnectAmount(), collect.getHandleAmount()));
            // 有效沟通率
            collect.setCommRate(ConstantParam.ConstantZeroPointZeroZero);
            if(collect.getConnectAmount() != null && collect.getConnectAmount() > ConstantParam.ConstantZero)
                collect.setCommRate(divide(collect.getCommAmount(), collect.getConnectAmount()));
            // 有效沟通率（ 全局 ）
            collect.setGlobalCommRate(divide(collect.getCommAmount(), collect.getHandleAmount()));
            // 用户转化率
            collect.setConvertRate(divide(collect.getUserAmount(), collect.getHandleAmount()));
            // 平均处理时间
            collect.setAverageTime(ConstantParam.ConstantZero);
            if(collect.getTotalTime() != null) {
                collect.setAverageTime(divideByInt(collect.getTotalTime(), collect.getHandleAmount()));
            } else
                collect.setTotalTime(ConstantParam.ConstantZero);
            // 平均处理时间（ 客服 ）
            collect.setCustomerTime(divides(divideByInt(collect.getTotalTime(), collect.getCustomerAmount()), ConstantParam.ConstantSixty));
            // 平均处理量（ 客服 ）
            collect.setAverageAmount(divideByInt(collect.getHandleAmount(), collect.getCustomerAmount()));
            collect.setGenerateTime(obtainStartTime());
            collect.setCreateTime(new Date());
            if(!collectMapper.saveEntity(collect))
                log.info(dateTime.concat("（ 管理 ）历史数据生成失败... "));
        } else
            log.info(dateTime.concat("（ 管理 ）汇总历史数据失败, 未统计到历史数据... "));
        log.info(dateTime.concat("（ 管理 ）历史数据统计处理完成. "));
    }

    /**
     * 汇总数据
     * -- 历史数据
     * -- 客服
     */
    protected void collectHistoryC() {
        String time = obtainStartTime(), dateTime = obtainDateTime();
        log.info(dateTime.concat("（ 客服 ）历史数据统计开始处理... "));
        StringBuffer sql = new StringBuffer("1 = 1").append(" and begin_time >= DATE_FORMAT('" + obtainStartTime(time) + "','%Y-%m-%d %H:%i:%s')").append(" and begin_time <= DATE_FORMAT('" + obtainEndTime(time) + "','%Y-%m-%d %H:%i:%s')");
        List<Report> reportSet = reportMapper.collectReportC(sql.toString());
        reportSet.stream().map(target -> {
            // 接通率
            target.setConnectRate(divide(target.getConnectAmount(), target.getHandleAmount()));
            // 有效沟通率
            target.setCommRate(ConstantParam.ConstantZeroPointZeroZero);
            if(target.getConnectAmount() != null && target.getConnectAmount() > ConstantParam.ConstantZero)
                target.setCommRate(divide(target.getCommAmount(), target.getConnectAmount()));
            // 有效沟通率（ 全局 ）
            target.setGlobalCommRate(divide(target.getCommAmount(), target.getHandleAmount()));
            // 用户转化率
            target.setConvertRate(divide(target.getUserAmount(), target.getHandleAmount()));
            // 平均处理时间
            target.setAverageTime(ConstantParam.ConstantZero);
            if(target.getTotalTime() != null) {
                target.setAverageTime(divideByInt(target.getTotalTime(), target.getHandleAmount()));
            } else {
            	target.setTotalTime(ConstantParam.ConstantZero);
            }
            target.setGenerateTime(time);
            target.setCreateTime(new Date());
            if(!reportMapper.saveEntity(target))
                log.info(dateTime.concat("客服：" + target.getUserId()).concat(" 历史数据生成失败..."));
            return target;
        }).collect(Collectors.toList());
        log.info(dateTime.concat("（ 客服 ）历史数据统计处理完成. "));
    }

    /**
     * 汇总数据
     * -- 全局数据
     * -- 管理
     */
    protected void collectGlobalM() {
        String dateTime = obtainDateTime();
        log.info(dateTime.concat("（ 管理 ）全局数据统计开始处理... "));
        GlobalCollect collect = collectMapper.collectGlobalM();
        if(collect != null) {
            // 接通率
            collect.setConnectRate(divide(collect.getConnectAmount(), collect.getHandleAmount()));
            // 有效沟通率
            collect.setCommRate(ConstantParam.ConstantZeroPointZeroZero);
            if(collect.getConnectAmount() != null && collect.getConnectAmount() > ConstantParam.ConstantZero)
                collect.setCommRate(divide(collect.getCommAmount(), collect.getConnectAmount()));
            // 有效沟通率（ 全局 ）
            collect.setGlobalCommRate(divide(collect.getCommAmount(), collect.getHandleAmount()));
            // 用户转化率
            collect.setConvertRate(divide(collect.getUserAmount(), collect.getHandleAmount()));
            // 平均处理时间
            collect.setAverageTime(ConstantParam.ConstantZero);
            if(collect.getTotalDateTime() != null) {
                collect.setAverageTime(divideByInt(collect.getTotalDateTime(), collect.getHandleAmount()));
            } else
                collect.setTotalDateTime(ConstantParam.ConstantZero);
            List<GlobalCollect> collectSet = globalMapper.obtainEntity("scope = 0 and userId = 0");
            if(collectSet != null && !collectSet.isEmpty() && collectSet.size() > ConstantParam.ConstantZero) {
                GlobalCollect item = collectSet.get(ConstantParam.ConstantZero);
                collect.setId(item.getId());
                BeanUtils.copyProperties(collect, item);
                item.setModifyTime(new Date());
                if(!globalMapper.updateEntity(item))
                    log.info(dateTime.concat("（ 管理 ）全局数据更新失败..."));
            } else {
                collect.setInfoM();
                if(!globalMapper.saveEntity(collect))
                    log.info(dateTime.concat("（ 管理 ）全局数据生成失败..."));
            }
        } else
            log.info(dateTime.concat("（ 管理 ）汇总全局数据失败, 未统计到历史数据... "));
        log.info(dateTime.concat("（ 管理 ）全局数据统计处理完成. "));
    }

    /**
     * 汇总数据
     * -- 全局数据
     * -- 客服
     */
    protected void collectGlobalC() {
        String dateTime = obtainDateTime();
        log.info(dateTime.concat("（ 客服 ）全局数据统计开始处理... "));
        List<GlobalDto> dtoSet = reportMapper.collectGlobalC();
        dtoSet.stream().map(target -> {
            GlobalCollect collect = convertDataModel(target, GlobalCollect.class);
            // 接通率
            collect.setConnectRate(divide(collect.getConnectAmount(), collect.getHandleAmount()));
            // 有效沟通率
            collect.setCommRate(ConstantParam.ConstantZeroPointZeroZero);
            if(collect.getConnectAmount() != null && collect.getConnectAmount() > ConstantParam.ConstantZero)
                collect.setCommRate(divide(collect.getCommAmount(), collect.getConnectAmount()));
            // 有效沟通率（ 全局 ）
            collect.setGlobalCommRate(divide(collect.getCommAmount(), collect.getHandleAmount()));
            // 用户转化率
            collect.setConvertRate(divide(collect.getUserAmount(), collect.getHandleAmount()));
            // 平均处理时间
            collect.setAverageTime(ConstantParam.ConstantZero);
            if(collect.getTotalDateTime() != null) {
                collect.setAverageTime(divideByInt(collect.getTotalDateTime(), collect.getHandleAmount()));
            } else
                collect.setTotalDateTime(ConstantParam.ConstantZero);
            // 累计处理时间（ 时 ）
            collect.setTotalTime(divides(collect.getTotalDateTime(), 3600));
            // 平均处理时间（ 每日 : 分 ）
            collect.setDailyTime(divides(divideByInt(collect.getTotalDateTime(), target.getDailyAmount()), ConstantParam.ConstantSixty));
            if(collect.getUserId() != null && collect.getUserId() > ConstantParam.ConstantZero) {
                List<GlobalCollect> collectSet = globalMapper.obtainEntity("scope = 1 and userId = " + collect.getUserId());
                if(collectSet != null && !collectSet.isEmpty() && collectSet.size() > ConstantParam.ConstantZero) {
                    GlobalCollect item = collectSet.get(ConstantParam.ConstantZero);
                    collect.setId(item.getId());
                    BeanUtils.copyProperties(collect, item);
                    item.setModifyTime(new Date());
                    if(!globalMapper.updateEntity(item))
                        log.info(dateTime.concat("客服：" + target.getUserId()).concat(" 全局数据更新失败..."));
                } else {
                    collect.setInfoC();
                    if(!globalMapper.saveEntity(collect))
                        log.info(dateTime.concat("客服：" + target.getUserId()).concat(" 全局数据生成失败..."));
                }
            } else
                log.info(dateTime.concat("（ 客服 ）汇总全局数据失败, 客户主键不存在... "));
            return target;
        }).collect(Collectors.toList());
        log.info(dateTime.concat("（ 客服 ）全局数据统计处理完成. "));
    }


    /**
     * 获取起始时间
     * @param time 格式化的时间字符串(格式:yyyy-MM-dd)
     * @return 格式化的时间(格式:yyyy-MM-dd HH:mm:ss)
     */
    protected static String obtainStartTime(String time) {
        long startTime;
        try {
            Date dataTime = dateFormatToDay.parse(time);
            startTime = (dataTime.getTime() - 0 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("格式化起始时间的时间差异常", e);
            throw new OpenException("格式化起始时间字符格式错误(格式:yyyy-MM-dd) !!! ", ResultEnum.ClientError);
        }
        return dateFormatToSecond.format(startTime);
    }

    /**
     * 获取截止时间
     * @param time 格式化的时间字符串(格式:yyyy-MM-dd)
     * @return 格式化后的时间(格式:yyyy-MM-dd HH:mm:ss)
     */
    protected static String obtainEndTime(String time) {
        long endTime;
        try {
            Date dataTime = dateFormatToDay.parse(time);
            endTime = (dataTime.getTime() + 24 * 60 * 60 * 1000 - 1);
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("格式化截止时间的时间差异常", e);
            throw new OpenException("格式化截止时间字符格式错误(格式:yyyy-MM-dd) !!! ", ResultEnum.ClientError);
        }
        return dateFormatToSecond.format(endTime);
    }

    /**
     * 获取起始时间
     * @return 格式化的时间(格式:yyyy-MM-dd)
     */
    protected static String obtainStartTime() {
        long startTime;
        try {
            Date dataTime = dateFormatToDay.parse(dateFormatToDay.format(DateUtils.mulDateByDays(new Date(), ConstantParam.ConstantOne)));
            startTime = (dataTime.getTime() - 0 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("格式化起始时间的时间差异常", e);
            throw new OpenException("格式化起始时间字符格式错误(格式:yyyy-MM-dd) !!! ", ResultEnum.ClientError);
        }
        return dateFormatToDay.format(startTime);
    }

    /**
     * 获取起始时间
     * @return 格式化的时间(格式:yyyy-MM-dd)
     */
    protected static String obtainTime() {
        long startTime;
        try {
            Date dataTime = dateFormatToDay.parse(dateFormatToDay.format(new Date()));
            startTime = (dataTime.getTime() - 0 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("格式化起始时间的时间差异常", e);
            throw new OpenException("格式化起始时间字符格式错误(格式:yyyy-MM-dd) !!! ", ResultEnum.ClientError);
        }
        return dateFormatToDay.format(startTime);
    }

    /**
     * 获取起始时间
     * @return 格式化的时间(格式:yyyy-MM-dd)
     */
    protected static String obtainTime(Date time) {
        long startTime;
        try {
            Date dataTime = dateFormatToDay.parse(dateFormatToDay.format(time));
            startTime = (dataTime.getTime() - 0 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("格式化起始时间的时间差异常", e);
            throw new OpenException("格式化起始时间字符格式错误(格式:yyyy-MM-dd) !!! ", ResultEnum.ClientError);
        }
        return dateFormatToDay.format(startTime);
    }

    /**
     * 获取时间
     * @return 格式化的时间(格式:yyyy-MM-dd HH:mm:ss)
     */
    protected static String obtainDateTime() {
        return dateFormatToSecond.format(new Date());
    }

    /**
     * 获取指定日期的几天前的时间
     */
    protected static String obtainBeforeTime(Date date,int day){
    	Calendar now =Calendar.getInstance();
    	now.setTime(date);
    	now.set(Calendar.DATE,now.get(Calendar.DATE)-day);
    	return obtainTime(now.getTime());
    }

    /**
     * 提供（相对）精确的除法运算
     * @param paramOne 被除数
     * @param paramTwo 除数
     * @return 两个参数的商
     */
    protected static Double divide(Integer paramOne, Integer paramTwo) {
        return new BigDecimal(paramOne).divide(new BigDecimal(paramTwo), ConstantParam.ConstantTwo, RoundingMode.HALF_UP).multiply(new BigDecimal(ConstantParam.ConstantHundred)).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算
     * @param paramOne 被除数
     * @param paramTwo 除数
     * @return 两个参数的商
     */
    protected static Double divides(Integer paramOne, Integer paramTwo) {
        return new BigDecimal(paramOne).divide(new BigDecimal(paramTwo), ConstantParam.ConstantTwo, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算
     * @param paramOne 被除数
     * @param paramTwo 除数
     * @return 两个参数的商
     */
    protected static Integer divideByInt(Integer paramOne, Integer paramTwo) {
        return new BigDecimal(paramOne).divide(new BigDecimal(paramTwo), ConstantParam.ConstantTwo, RoundingMode.HALF_UP).intValue();
    }

    /**
     * 提供（相对）精确的除法运算
     * @param paramOne 被除数
     * @param paramTwo 除数
     * @return 两个参数的商
     */
    protected static Integer divide(Double paramOne, Double paramTwo) {
        return new BigDecimal(paramOne).divide(new BigDecimal(paramTwo), ConstantParam.ConstantTwo, RoundingMode.HALF_UP).intValue();
    }

    /**
     * 提供精确的乘法运算
     * @param paramOne 被乘数
     * @param paramTwo 乘数
     * @return 两个参数的积
     */
    protected static Double multiply(Double paramOne, Double paramTwo) {
        return new BigDecimal(paramOne).multiply(new BigDecimal(paramTwo)).doubleValue();
    }

    /**
     * 提供精确的加法运算
     * @param paramOne 被加数
     * @param paramTwo 加数
     * @return 两个参数的和
     */
    protected static Integer add(Integer paramOne, Integer paramTwo){
        return new BigDecimal(paramOne).add(new BigDecimal(paramTwo)).intValue();
    }

    /**
     * 提供精确的加法运算
     * @param paramOne 被加数
     * @param paramTwo 加数
     * @return 两个参数的和
     */
    protected static Double add(Double paramOne, Double paramTwo){
        return new BigDecimal(paramOne).add(new BigDecimal(paramTwo)).doubleValue();
    }


    /**
     * 校验主键参数
     * @param key 主键参数
     */
    protected static void verifyMainKey(Integer key) {
        verifyMainKey(key, "错误的主键值");
    }

    /**
     * 校验主键参数
     * @param key 主键参数
     * @param msg 错误消息
     */
    protected static void verifyMainKey(Integer key, String msg) {
        if (!(key != null && key > ConstantParam.ConstantZero)) throw new OpenException(msg, ResultEnum.ClientError);
    }

    /**
     * 转换数据模型
     * -- 对象
     * @param source 需要转换的对象
     * @param targetClazz 需要转换成的对象
     * @return 处理结果
     */
    protected static <T> T convertDataModel(Object source, Class<T> targetClazz) {
        if (source == null) return null;
        try {
            T target = targetClazz.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 转换数据模型
     * -- 集合
     * @param sources 需要转换的集合
     * @param targetClazz 需要转换成的集合
     * @return 处理结果
     */
    protected static <T> List<T> convertDataModel(List<? extends Object> sources, Class<T> targetClazz) {
        if (sources == null) return null;
        List<T> targets = new ArrayList(sources.size());
        for (Object source : sources) {
            try {
                targets.add(convertDataModel(source, targetClazz));
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return targets;
    }
}
