package com.anniu.ordercall.service.biz.impl;


import com.anniu.mid.freework.container.spring.web.exception.ApiException;
import com.anniu.ordercall.bean.enums.ResultEnum;
import com.anniu.ordercall.bean.tool.OpenException;
import com.anniu.ordercall.bean.vo.biz.CallOrderVo;
import com.anniu.ordercall.core.enums.CallResultEnum;
import com.anniu.ordercall.core.enums.DealResultEnum;
import com.anniu.ordercall.core.utils.DateUtils;
import com.anniu.ordercall.core.utils.IdCardUtils;
import com.anniu.ordercall.model.moneyarrive.MoneyarriveOrder;
import com.anniu.ordercall.model.ordercall.CallOrder;
import com.anniu.ordercall.repository.moneyarrive.MoneyarriveOrderMapper;
import com.anniu.ordercall.repository.ordercall.CallOrderMapper;
import com.anniu.ordercall.service.biz.WorkbenchService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.anniu.ordercall.core.exception.BizExceptionEnum.NO_NEW_ORDER;
import static com.anniu.ordercall.core.utils.DateUtils.YYYY_MM_DD_HH_MM_SS;

@Service
public class WorkbenchServiceImpl implements WorkbenchService {
	private static final SimpleDateFormat dateFormatToDay = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private MoneyarriveOrderMapper moneyarriveOrderMapper;

    @Autowired
    private CallOrderMapper callOrderMapper;

    @Override
    public List<MoneyarriveOrder> getOrder(Integer serviceId) {
        List<MoneyarriveOrder> resultList = new LinkedList<>();
        List<CallOrder> orderList = callOrderMapper.selectByServiceId(serviceId);
        if (CollectionUtils.isNotEmpty(orderList)) {
            CallOrder callOrder = orderList.get(0);
            MoneyarriveOrder moneyarriveOrder = new MoneyarriveOrder();
            moneyarriveOrder.setAge(callOrder.getAge());
            moneyarriveOrder.setCallOrderId(callOrder.getId());
            moneyarriveOrder.setOrderId(callOrder.getOrderId());
            moneyarriveOrder.setDeviceType(callOrder.getDeviceType());
            moneyarriveOrder.setIdCardNum(callOrder.getIdCardNum());
            moneyarriveOrder.setOrderSource(callOrder.getOrderSource());
            moneyarriveOrder.setOrderTime(callOrder.getOrderTime());
            moneyarriveOrder.setOrderType(callOrder.getOrderType());
            moneyarriveOrder.setRegisterTime(callOrder.getRegisterTime());
            moneyarriveOrder.setSex(callOrder.getSex());
            moneyarriveOrder.setUserName(callOrder.getUserName());
            moneyarriveOrder.setUserPhone(callOrder.getUserPhone());
            moneyarriveOrder.setPayStatus(callOrder.getPayStatus());
            resultList.add(moneyarriveOrder);
            return resultList;
        }
        List<MoneyarriveOrder> moneyarriveOrders = sortOrder(getOrderFromXYZC());
        if (CollectionUtils.isNotEmpty(moneyarriveOrders)) {
            MoneyarriveOrder moneyarriveOrder = moneyarriveOrders.get(0);
            CallOrder callOrder = new CallOrder();
            callOrder.setBeginTime(new Date());
            callOrder.setAge(new IdCardUtils.IdCardInfoEntity(moneyarriveOrder.getIdCardNum()).getAge());
            callOrder.setSex(new IdCardUtils.IdCardInfoEntity(moneyarriveOrder.getIdCardNum()).getGender());
            callOrder.setDeviceType(moneyarriveOrder.getDeviceType());
            callOrder.setOrderId(moneyarriveOrder.getOrderId());
            callOrder.setOrderSource(moneyarriveOrder.getOrderChannel());
            callOrder.setRegisterTime(moneyarriveOrder.getRegisterTime());
            callOrder.setUserId(moneyarriveOrder.getUserId());
            callOrder.setUserName(moneyarriveOrder.getUserName());
            callOrder.setOrderTime(moneyarriveOrder.getOrderTime());
            callOrder.setServiceId(serviceId);
            callOrder.setIdCardNum(moneyarriveOrder.getIdCardNum());
            callOrder.setUserPhone(moneyarriveOrder.getUserPhone());
            callOrder.setOrderType(moneyarriveOrder.getOrderType());
            callOrder.setPayStatus(0);
            callOrder.setOrderNum(0);
            callOrder.setOrderAmount(0);
            callOrderMapper.insert(callOrder);
            moneyarriveOrder.setCallOrderId(callOrder.getId());
            resultList.add(moneyarriveOrder);
        } else {
            throw new ApiException(NO_NEW_ORDER.getCode(), NO_NEW_ORDER.getMessage());
        }
        return resultList;
    }

    @Override
    public void submitOrder(String callResult, String dealResult, Integer callOrderId) {
        CallOrder callOrder = callOrderMapper.selectByPrimaryKey(callOrderId);
        Long dealTime = ((new Date().getTime() - callOrder.getBeginTime().getTime()) / 1000);
        CallOrder entity = new CallOrder();
        entity.setId(callOrderId);
        entity.setCallResult(CallResultEnum.getCodeByValue(callResult));
        entity.setDealResult(DealResultEnum.getCodeByValue(dealResult));
        entity.setEndTime(new Date());
        entity.setDealTime(dealTime.intValue());
        callOrderMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public PageInfo<CallOrderVo> historyRecord(Integer pageNum, Integer pageSize, Integer serviceId) {
        PageHelper.startPage(pageNum, pageSize);
        List<CallOrderVo> callOrderVoList = callOrderMapper.historyRecord(serviceId);
        callOrderVoList.stream().map(target -> {
            target.setBeginTime(DateUtils.format(target.getBeginTimeDate(), YYYY_MM_DD_HH_MM_SS));
            target.setOrderTime(DateUtils.format(target.getOrderTimeDate(), YYYY_MM_DD_HH_MM_SS));
            target.setDealResult(DealResultEnum.getValueByCode(target.getDealResultInteger()));
            return target;
        }).collect(Collectors.toList());
        return new PageInfo<>(callOrderVoList);
    }

    /**
     * 符合条件订单信息
     */
    private List<MoneyarriveOrder> getOrderFromXYZC() {
        Date twoMinuteAgo = DateUtils.mulDateByMinute(new Date(), 2);
        Date todayZeroTime = DateUtils.setDateByStr(DateUtils.getDay(new Date()) + " 00:00:00", YYYY_MM_DD_HH_MM_SS);
        return moneyarriveOrderMapper.getOrder(todayZeroTime, twoMinuteAgo);
    }

    /**
     * 条件筛选
     */
    private List<MoneyarriveOrder> sortOrder(List<MoneyarriveOrder> xyzcOrderInfoDtoList) {
        Set<Integer> userIdSet = new HashSet<>();
        if (CollectionUtils.isNotEmpty(xyzcOrderInfoDtoList)) {
            for (MoneyarriveOrder xyzcOrderInfoDto : xyzcOrderInfoDtoList) {
                userIdSet.add(xyzcOrderInfoDto.getUserId());
            }
            Date oneDayAgo = DateUtils.mulDateByDays(new Date(), 1);
            List<Integer> callOrderList = callOrderMapper.selectOutUser(oneDayAgo, userIdSet);
            xyzcOrderInfoDtoList = xyzcOrderInfoDtoList.stream().filter(target -> !callOrderList.contains(target.getUserId())).collect(Collectors.toList());
            //根据时间
            Collections.sort(xyzcOrderInfoDtoList, new Comparator<MoneyarriveOrder>() {
                @Override
                public int compare(MoneyarriveOrder x1, MoneyarriveOrder x2) {
                    Date date1 = x1.getOrderTime();
                    Date date2 = x2.getOrderTime();
                    return date2.compareTo(date1);
                }
            });
            //根据是否登录手机号与查询手机号是否一致
            Collections.sort(xyzcOrderInfoDtoList, new Comparator<MoneyarriveOrder>() {
                @Override
                public int compare(MoneyarriveOrder x1, MoneyarriveOrder x2) {
                    return x1.getUserPhone().equalsIgnoreCase(x1.getUserPhone()) ? 1 : -1;
                }
            });
        }
        return xyzcOrderInfoDtoList;
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
            throw new OpenException("格式化起始时间字符格式错误(格式:yyyy-MM-dd) !!! ", ResultEnum.ClientError);
        }
        return dateFormatToDay.format(startTime);
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
    
	@Override
	public List<Integer> vDayReports(Integer serviceId) {
		StringBuilder sql=new StringBuilder(" begin_time >=DATE_FORMAT('");
		sql.append(obtainBeforeTime(new Date(),6)).append("','%Y-%m-%d')");
		sql.append(" and  begin_time<=DATE_FORMAT('").append(obtainBeforeTime(new Date(),1)).append("','%Y-%m-%d') ");
		return callOrderMapper.vDayReports(serviceId, sql.toString());
	}

    
    /**
     * 获取前五天的成交记录
     */
	@Override
	public PageInfo<CallOrder> vDayRecord(Integer pageNum, Integer pageSize, Integer userId) {
		PageHelper.startPage(pageNum, pageSize);
		StringBuilder sql=new StringBuilder(" service_id = ").append(userId);
		sql.append(" and  begin_time >=DATE_FORMAT('").append(obtainBeforeTime(new Date(),6)).append("','%Y-%m-%d')");
		sql.append(" and  begin_time<=DATE_FORMAT('").append(obtainBeforeTime(new Date(),1)).append("','%Y-%m-%d') ");
		return new PageInfo<>(callOrderMapper.vDayRecord(sql.toString()));
	}

	@Override
	public List<Integer> refundRatio(Integer serviceId) {
		return callOrderMapper.refundRatio(serviceId, obtainBeforeTime(new Date(),1));
	}

	@Override
	public PageInfo<CallOrder> searchOrderByPhone(Integer pageNum, Integer pageSize, String userPhone) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo<>(callOrderMapper.searchOrderByPhone(userPhone));
	}

	@Override
	public Integer updateEntity(String orderId, Integer payStatus) {
		return callOrderMapper.updateEntity(orderId, payStatus);
	}

}