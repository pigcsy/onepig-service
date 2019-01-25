package com.one.pig.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * 开发公司：anniu在线工具 <p>
 * 版权所有：© www.anniu.com<p>
 * 博客地址：http://www.anniu.com/blog/  <p>
 * <p>
 * <p>
 * Session 状态 VO
 * <p>
 * <p>
 * <p>
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　csy　2016年6月2日 　<br/>
 *
 * @author csy
 * @version 1.0, 2016年6月2日 <br/>
 * @email so@anniu.com
 */
public class SessionStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    //是否踢出 true:有效，false：踢出。
    private Boolean onlineStatus = Boolean.TRUE;


    public Boolean isOnlineStatus() {
        return onlineStatus;
    }

    public Boolean getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(Boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }


}
