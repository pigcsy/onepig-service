package com.one.pig.core.shiro.listenter;


import com.anniu.ordercall.core.shiro.session.ShiroSessionRepository;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 开发公司：anniu在线工具 <p>
 * 版权所有：© www.anniu.com<p>
 * 博客地址：http://www.anniu.com/blog/  <p>
 * <p>
 * <p>
 * shiro 回话 监听
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
@Component
public class CustomSessionListener implements SessionListener {
    @Autowired
    private ShiroSessionRepository shiroSessionRepository;

    /**
     * 一个回话的生命周期开始
     */
    @Override
    public void onStart(Session session) {
        //TODO
        System.out.println("on start");
    }

    /**
     * 一个回话的生命周期结束
     */
    @Override
    public void onStop(Session session) {
        //TODO
        System.out.println("on stop");
    }

    @Override
    public void onExpiration(Session session) {
        shiroSessionRepository.deleteSession(session.getId());
    }

    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }

    public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }

}

