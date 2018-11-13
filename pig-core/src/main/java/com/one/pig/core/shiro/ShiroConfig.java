package com.one.pig.core.shiro;

import com.one.pig.core.shiro.cache.ShiroCache;
import com.one.pig.core.shiro.filter.LoginFilter;
import com.one.pig.core.shiro.filter.PermissionFilter;
import com.one.pig.core.shiro.filter.RoleFilter;
import com.one.pig.core.shiro.filter.SimpleAuthFilter;
import com.one.pig.core.shiro.service.impl.ShiroManagerImpl;
import com.one.pig.core.shiro.token.ShiroRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisOperations;

import javax.servlet.Filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    public static final int TIME_OUT_SECOND = 43200;
    public static final String SESSION_ID_NAME = "ORDER_CALL_JSESSIONID";

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 项目自定义的Realm
     */
    @Bean
    public ShiroRealm shiroRealm() {
        return new ShiroRealm();
    }

    @Bean
    @Autowired
    public CacheManager shiroCacheManager(@Qualifier("redisTemplate") RedisOperations redisOperations) {
        return new CacheManager() {
            @Override
            public <K, V> Cache<K, V> getCache(String name) throws CacheException {
                return new ShiroCache(name, TIME_OUT_SECOND, redisOperations);
            }
        };
    }

    @Bean
    @Autowired
    public DefaultWebSecurityManager securityManager(CookieRememberMeManager cookieRememberMeManager, CacheManager cacheManager) {
        EnterpriseCacheSessionDAO ecsd = new EnterpriseCacheSessionDAO();
        ecsd.setCacheManager(cacheManager);
        WebSessionManager webSessionManager = new WebSessionManager();
        webSessionManager.setSessionDAO(ecsd);
        webSessionManager.setGlobalSessionTimeout(TIME_OUT_SECOND * 1000);
        webSessionManager.setSessionValidationSchedulerEnabled(false);
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(this.shiroRealm());
        securityManager.setCacheManager(cacheManager);
        securityManager.setSessionManager(webSessionManager);
        securityManager.setRememberMeManager(cookieRememberMeManager);
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }

    /**
     * rememberMe管理器, cipherKey生成见{@code Base64Test.java}
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(SimpleCookie rememberMeCookie) {
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
        manager.setCookie(rememberMeCookie);
        return manager;
    }

    /**
     * 记住密码Cookie
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setMaxAge(24 * 60 * 60);//7天
        return simpleCookie;
    }

    @Bean("shiroFilter")
    @Autowired
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager((org.apache.shiro.mgt.SecurityManager) securityManager);
        Map<String, String> fcdm = new LinkedHashMap<>();
        fcdm.put("/system/login", "anon");
        fcdm.put("/outbound-workbench/updatePayStatus", "anon");
        fcdm.put("/**", "loginFilter");
        fcdm.put("/**", "roleFilter");
        fcdm.put("/**", "permissionFilter");
        fcdm.put("/**", "simpleFilter");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(fcdm);
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        filters.put("loginFilter", new LoginFilter());
        filters.put("roleFilter", new RoleFilter());
        filters.put("permissionFilter", new PermissionFilter());
        filters.put("simpleFilter", new SimpleAuthFilter());
        return shiroFilterFactoryBean;
    }

    private static class WebSessionManager extends DefaultWebSessionManager {

        private Cookie sessionIdCookie;


        public WebSessionManager() {
            super();
            Cookie cookie = new SimpleCookie(SESSION_ID_NAME);
            cookie.setHttpOnly(true);
            this.sessionIdCookie = cookie;
        }

        @Override
        public Cookie getSessionIdCookie() {
            return this.sessionIdCookie;
        }

        @Override
        protected Serializable getSessionId(ServletRequest servletRequest, ServletResponse servletResponse) {
            return ShiroManagerImpl.getSessionId(servletRequest);
        }
    }

}