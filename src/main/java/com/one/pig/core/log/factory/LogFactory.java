package com.one.pig.core.log.factory;

import com.anniu.ordercall.bean.doc.LogMongoDoc;
import com.anniu.ordercall.core.enums.BizLogTypeEnum;
import com.anniu.ordercall.core.enums.LogSucceedEnum;
import com.anniu.ordercall.core.enums.LogTypeEnum;

import java.util.Date;

/**
 * 日志对象创建工厂
 *
 * @author csy
 */
public class LogFactory {

    /**
     * 创建操作日志
     *
     * @author csy
     * @Date 2017/3/30 18:45
     */
    public static LogMongoDoc createOperationLog(BizLogTypeEnum logType, Integer userId, String userName, String bussinessName, String clazzName, String methodName, String msg, LogSucceedEnum succeed) {
        LogMongoDoc logMongoDoc = new LogMongoDoc();
        logMongoDoc.setLogType(logType.getMessage());
        logMongoDoc.setLogName(bussinessName);
        logMongoDoc.setUserName(userName);
        logMongoDoc.setUserId(userId);
        logMongoDoc.setClassName(clazzName);
        logMongoDoc.setMethod(methodName);
        logMongoDoc.setCreateTime(new Date());
        logMongoDoc.setSucceed(succeed.getMessage());
        logMongoDoc.setMessage(msg);
        return logMongoDoc;
    }

    /**
     * 创建登录日志
     *
     * @author csy
     * @Date 2017/3/30 18:46
     */
    public static LogMongoDoc createLoginLog(LogTypeEnum logType, Integer userId, String userName, String msg, String ip) {
        LogMongoDoc logMongoDoc = new LogMongoDoc();
        logMongoDoc.setMessage(logType.getMessage());
        logMongoDoc.setUserName(userName);
        logMongoDoc.setUserId(userId);
        logMongoDoc.setCreateTime(new Date());
        logMongoDoc.setSucceed(LogSucceedEnum.SUCCESS.getMessage());
        logMongoDoc.setIp(ip);
        logMongoDoc.setMessage(msg);
        return logMongoDoc;
    }
}
