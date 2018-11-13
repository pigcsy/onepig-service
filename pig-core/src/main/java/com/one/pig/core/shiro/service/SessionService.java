package com.one.pig.core.shiro.service;

import com.one.pig.core.bean.UserDto;
import com.one.pig.core.shiro.ShiroConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SessionService {
    private static final String KEY_CURRENT_USER_ID = "ORDER_CALL_USER_ID";
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisOperations redisOperations;

    public static String getSessionId(ServletRequest request) {
        String sessionId = null;
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            sessionId = httpServletRequest.getHeader("sessionId");
            sessionId = StringUtils.trimToNull(sessionId);
            if ("null".equals(sessionId) || StringUtils.length(sessionId) < 10) {
                sessionId = null;
            }
            if (sessionId == null && httpServletRequest.getCookies() != null) {
                for (Cookie cookie : httpServletRequest.getCookies()) {
                    if (ShiroConfig.SESSION_ID_NAME.equalsIgnoreCase(cookie.getName())) {
                        sessionId = cookie.getValue();
                        break;
                    }
                }
            }
        }
        return sessionId;
    }

    public UserDto getLoginObject(String sessionId) {
        return (UserDto) getAttribute(KEY_CURRENT_USER_ID, sessionId);
    }

    public UserDto getLoginObject() {
        return (UserDto) getAttribute(KEY_CURRENT_USER_ID);
    }

    public UserDto getSysUser() {
        return (UserDto) getAttribute("KEY_CURRENT_USER_ID");
    }

    public String getSessionId() {
        return SecurityUtils.getSubject().getSession().getId().toString();
    }

    public void setAttribute(Object key, Object value, String sessionId) {
        if (StringUtils.isBlank(sessionId) || value == null) {
            return;
        }
        redisOperations.opsForHash().put("sessionAttrs:" + sessionId, key, value);

        redisOperations.expire("sessionAttrs:" + sessionId, ShiroConfig.TIME_OUT_SECOND, TimeUnit.SECONDS);
    }

    public boolean removeAttribute(Object key, String sessionId) {
        if (StringUtils.isBlank(sessionId)) {
            return false;
        }
        return redisOperations.opsForHash().delete("sessionAttrs:" + sessionId, key) == 1;
    }

    public Object getAttribute(Object key, String sessionId) {
        if (StringUtils.isBlank(sessionId)) {
            return null;
        }
        Object o = null;
        try {
            o = redisOperations.opsForHash().get("sessionAttrs:" + sessionId, key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return o;
    }

    public void setAttribute(Object key, Object value) {
        setAttribute(key, value, getSessionId());
    }

    public boolean removeAttribute(Object key) {
        return removeAttribute(key, getSessionId());
    }

    public Object getAttribute(Object key) {
        return getAttribute(key, getSessionId());
    }

    public void selfLogout(String userId) {
        String sessionId = getSessionId();
        String type = (String) getAttribute("loginChannel", sessionId);
        SecurityUtils.getSubject().logout();
        String key;
        if (StringUtils.isBlank(type)) {
            key = "sessionIdList:" + userId;
        } else {
            key = "sessionIdList:" + userId + ":" + type;
        }
        deleteSession(key, sessionId);
        checkClear(key);
    }

    public void checkClear(String key) {
        List<String> sessionIds;
        sessionIds = redisOperations.opsForList().range(key, 0, -1);
        if (sessionIds != null && sessionIds.size() == 0) {
            redisOperations.delete(key);
        }
    }

    public void deleteSession(String key, String sessionId) {
        redisOperations.opsForList().remove(key, 0, sessionId);
        redisOperations.delete(CachingSessionDAO.ACTIVE_SESSION_CACHE_NAME + ":" + sessionId);
    }


    private void kickOut(String key, String keepSessionId, List<String> sessionIds) {
        if (sessionIds != null) {
            for (String sessionId : sessionIds) {
                if (!StringUtils.equals(sessionId, keepSessionId)) {
                    deleteSession(key, sessionId);
                    setAttribute("isKickOut", "true", sessionId);
                }
            }
        }
    }

    public void logoutUser(String userId) {
        String key;
        List<String> sessionIds;

        key = "sessionIdList:" + userId;
        sessionIds = redisOperations.opsForList().range(key, 0, -1);
        logoutUser(key, sessionIds);

        key = "sessionIdList:" + userId + ":H5";
        sessionIds = redisOperations.opsForList().range(key, 0, -1);
        logoutUser(key, sessionIds);

        key = "sessionIdList:" + userId + ":APP";
        sessionIds = redisOperations.opsForList().range(key, 0, -1);
        logoutUser(key, sessionIds);
    }

    private void logoutUser(String key, List<String> sessionIds) {
        if (sessionIds != null) {
            for (String sessionId : sessionIds) {
                deleteSession(key, sessionId);
            }
        }
    }
}
