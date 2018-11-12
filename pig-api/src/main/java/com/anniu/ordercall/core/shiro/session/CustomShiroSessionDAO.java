package com.anniu.ordercall.core.shiro.session;


import com.anniu.ordercall.core.utils.LoggerUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 * Session 操作
 *
 * @author csy
 * @version 1.0, 2017年6月2日 <br/>
 * @email so@anniu.com
 */
@Component
public class CustomShiroSessionDAO extends AbstractSessionDAO {
    @Autowired
    private ShiroSessionRepository shiroSessionRepository;

    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }

    public void setShiroSessionRepository(
            ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        getShiroSessionRepository().saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if (session == null) {
            LoggerUtils.error(getClass(), "Session 不能为null");
            return;
        }
        Serializable id = session.getId();
        if (id != null)
            getShiroSessionRepository().deleteSession(id);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        return getShiroSessionRepository().getAllSessions();
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        getShiroSessionRepository().saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return getShiroSessionRepository().getSession(sessionId);
    }
}
