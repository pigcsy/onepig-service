package com.one.pig.core.log.factory;


import com.anniu.ordercall.bean.doc.LogMongoDoc;
import com.anniu.ordercall.core.enums.BizLogTypeEnum;
import com.anniu.ordercall.core.enums.LogSucceedEnum;
import com.anniu.ordercall.core.enums.LogTypeEnum;
import com.anniu.ordercall.core.log.LogManager;
import com.anniu.ordercall.core.utils.ToolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mongodb.core.MongoOperations;

import javax.annotation.PostConstruct;
import java.util.TimerTask;

/**
 * 日志操作任务创建工厂
 *
 * @author csy
 * @date 2016年12月6日 下午9:18:27
 */
public class LogTaskFactory implements ApplicationContextAware {

    private static MongoOperations mongoOperations;

    private static Logger logger = LoggerFactory.getLogger(LogManager.class);

    private ApplicationContext applicationContext;

    public static TimerTask loginLog(final Integer userId, final String userName, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    LogMongoDoc logMongoDoc = LogFactory.createLoginLog(LogTypeEnum.LOGIN, userId, userName, null, ip);
                    mongoOperations.insert(logMongoDoc);
                } catch (Exception e) {
                    logger.error("创建登录日志异常!", e);
                }
            }
        };
    }

    public static TimerTask loginLog(final String userName, final String msg, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                LogMongoDoc logMongoDoc = LogFactory.createLoginLog(
                        LogTypeEnum.LOGIN_FAIL, null, userName, "账号:" + userName + "," + msg, ip);
                try {
                    mongoOperations.insert(logMongoDoc);
                } catch (Exception e) {
                    logger.error("创建登录失败异常!", e);
                }
            }
        };
    }

    public static TimerTask exitLog(final Integer userId, final String userName, final String ip) {
        return new TimerTask() {
            @Override
            public void run() {
                LogMongoDoc logMongoDoc = LogFactory.createLoginLog(LogTypeEnum.EXIT, userId, userName, null, ip);
                try {
                    mongoOperations.insert(logMongoDoc);
                } catch (Exception e) {
                    logger.error("创建退出日志异常!", e);
                }
            }
        };
    }

    public static TimerTask bussinessLog(final Integer userId, final String userName, final String bussinessName, final String clazzName, final String methodName, final String msg) {
        return new TimerTask() {
            @Override
            public void run() {
                LogMongoDoc logMongoDoc = LogFactory.createOperationLog(
                        BizLogTypeEnum.BUSSINESS, userId, userName, bussinessName, clazzName, methodName, msg, LogSucceedEnum.SUCCESS);
                try {
                    mongoOperations.insert(logMongoDoc);
                } catch (Exception e) {
                    logger.error("创建业务日志异常!", e);
                }
            }
        };
    }

    public static TimerTask exceptionLog(final Integer userId, final String userName, final Exception exception) {
        return new TimerTask() {
            @Override
            public void run() {
                String msg = ToolUtils.getExceptionMsg(exception);
                LogMongoDoc logMongoDoc = LogFactory.createOperationLog(
                        BizLogTypeEnum.EXCEPTION, userId, userName, "", null, null, msg, LogSucceedEnum.FAIL);
                try {
                    mongoOperations.insert(logMongoDoc);
                } catch (Exception e) {
                    logger.error("创建异常日志异常!", e);
                }
            }
        };
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        mongoOperations = applicationContext.getBean(MongoOperations.class);
    }


}
