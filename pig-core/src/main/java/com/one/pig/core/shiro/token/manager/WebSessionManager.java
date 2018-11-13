package com.one.pig.core.shiro.token.manager;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class WebSessionManager extends DefaultWebSessionManager {

    private Cookie sessionIdCookie;


    public WebSessionManager() {
        super();
        Cookie cookie = new SimpleCookie("ordercall");
        cookie.setHttpOnly(true);
        this.sessionIdCookie = cookie;
    }

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
                for (javax.servlet.http.Cookie cookie : httpServletRequest.getCookies()) {
                    if ("ordercall".equalsIgnoreCase(cookie.getName())) {
                        sessionId = cookie.getValue();
                        break;
                    }
                }
            }
        }
        return sessionId;
    }

    @Override
    public Cookie getSessionIdCookie() {
        return this.sessionIdCookie;
    }

    @Override
    protected Serializable getSessionId(ServletRequest servletRequest, ServletResponse servletResponse) {
        return getSessionId(servletRequest);
    }
}