package com.anniu.ordercall.service.common.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.anniu.ordercall.core.utils.DateUtils;
import com.anniu.ordercall.model.moneyarrive.MoneyarriveOrder;
import com.anniu.ordercall.model.ordercall.CallOrder;
import com.anniu.ordercall.repository.moneyarrive.MoneyarriveOrderMapper;
import com.anniu.ordercall.repository.ordercall.CallOrderMapper;
import com.anniu.ordercall.service.common.TaskService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.anniu.ordercall.core.constant.CommonConstant.TIME_OUT_SECOND;
import static com.anniu.ordercall.core.constant.CommonConstant.XYZC_ORDER_INFO;
import static com.anniu.ordercall.core.utils.DateUtils.YYYY_MM_DD_HH;
import static com.anniu.ordercall.core.utils.DateUtils.YYYY_MM_DD_HH_MM_SS;

@Component
@Service
public class TaskServiceImpl implements TaskService {

    private static Logger log = LoggerFactory.getLogger(TaskServiceImpl.class);

    @Autowired
    RedisOperations redisOperations;

    @Autowired
    MoneyarriveOrderMapper moneyarriveOrderMapper;

    @Autowired
    CallOrderMapper callOrderMapper;

    @Override
    public void getOneOrderInfo() {
        JSONArray jsonArray = new JSONArray();
        String newTime = DateUtils.formatDate(new Date(), YYYY_MM_DD_HH);
        log.info(newTime + "原始数据开始生成");
        //如果第二天定时时跑前一天数据
        if (!newTime.substring(newTime.lastIndexOf(" ") + 1).equalsIgnoreCase("01")) {
            //获取前一个小时的订单用户数据
            Object redisInfo = redisOperations.opsForValue().get(XYZC_ORDER_INFO);
            if (redisInfo != null) {
                jsonArray = JSONArray.parseArray(redisInfo.toString());
            }
        }
        Date todayZeroTime = DateUtils.setDateByStr(DateUtils.getDay() + " 00:00:00", YYYY_MM_DD_HH_MM_SS);
        List<CallOrder> callOrderList = callOrderMapper.selectTodayData(todayZeroTime, new Date());
        Set set = new LinkedHashSet();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            List<Integer> list = (List) object.get("orderIdList");
            for (Integer orderId : list) {
                set.add(object.get("userId").toString() + orderId.toString());
            }
        }
        if (CollectionUtils.isNotEmpty(callOrderList)) {
            List<MoneyarriveOrder> moneyOrderList = moneyarriveOrderMapper.selectOrderInfo(callOrderList, todayZeroTime, new Date());
            //合并订单信息
            log.info(moneyOrderList + "moneyArriveOrderList");
            if (CollectionUtils.isNotEmpty(moneyOrderList)) {
                for (MoneyarriveOrder order : moneyOrderList) {
                    JSONObject jsonObject = new JSONObject();
                    //关联本系统客服id
                    boolean hasSameOne = false;
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        //做筛选 redis有的做更新新无得新增
                        if (order.getUserId() == object.getIntValue("userId")) {
                            if (set.contains(order.getUserId().toString() + order.getOrderId().toString())) {
                                hasSameOne = true;
                                break;
                            }
                            if (!DateUtils.formatDate(order.getOrderTime(), YYYY_MM_DD_HH_MM_SS).equalsIgnoreCase(object.getString("createTime"))) {
                                jsonObject.put("userId", order.getUserId());
                                jsonObject.put("createTime", DateUtils.formatDate(order.getOrderTime(), YYYY_MM_DD_HH_MM_SS));
                                jsonObject.put("orderNum", object.getIntValue("orderNum") + 1);
                                jsonObject.put("orderAmount", object.getIntValue("orderAmount") + order.getPayMoney().toString());
                                List<Integer> list = (List) object.get("orderIdList");
                                list.add(order.getOrderId());
                                jsonObject.put("orderIdList", list);
                                for (CallOrder callOrder : callOrderList) {
                                    if (order.getUserId().equals(callOrder.getUserId())) {
                                        jsonObject.put("serviceId", callOrder.getServiceId());
                                        jsonObject.put("id", callOrder.getId());
                                        break;
                                    }
                                }
                                jsonArray.remove(object);
                            }
                            hasSameOne = true;
                        }
                    }
                    if (!hasSameOne) {
                        List<Integer> list = new ArrayList();
                        jsonObject.put("userId", order.getUserId());
                        jsonObject.put("createTime", DateUtils.formatDate(order.getOrderTime(), YYYY_MM_DD_HH_MM_SS));
                        jsonObject.put("orderNum", 1);
                        jsonObject.put("orderAmount", order.getPayMoney().toString());
                        list.add(order.getOrderId());
                        jsonObject.put("orderIdList", list);
                        for (CallOrder callOrder : callOrderList) {
                            if (order.getUserId().equals(callOrder.getUserId())) {
                                jsonObject.put("serviceId", callOrder.getServiceId());
                                jsonObject.put("id", callOrder.getId());
                                break;
                            }
                        }
                    }
                    if (jsonObject != null && jsonObject.size() != 0)
                        jsonArray.add(jsonObject);
                }
            }
        }
        log.info(jsonArray.toJSONString() + "合并后数据");
        redisOperations.opsForValue().set(XYZC_ORDER_INFO, jsonArray.toJSONString(), TIME_OUT_SECOND, TimeUnit.SECONDS);
        log.info(newTime + "原始数据开始生成完成");
        //第二天0点持久
        if (newTime.substring(newTime.lastIndexOf(" ") + 1).equalsIgnoreCase("00")) {
            log.info(jsonArray.toJSONString() + "持久数据");
            if (CollectionUtils.isNotEmpty(jsonArray)) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    CallOrder callOrder = new CallOrder();
                    callOrder.setId(object.getIntValue("id"));
                    callOrder.setOrderNum(object.getIntValue("orderNum"));
                    callOrder.setOrderAmount(object.getIntValue("orderAmount"));
                    callOrderMapper.updateByPrimaryKeySelective(callOrder);
                }
            }
        }
    }

}
