package com.one.pig.core.shiro.service.impl;

import com.one.pig.core.shiro.ShiroConfig;
import com.one.pig.core.shiro.service.ShiroManager;
import com.one.pig.core.util.common.LoggerUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisOperations;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 开发公司：anniu在线工具 <p>
 * 版权所有：© www.anniu.com<p>
 * 博客地址：http://www.anniu.com/blog/  <p>
 * <p>
 * <p>
 * 动态加载权限 Service
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
public class ShiroManagerImpl implements ShiroManager {

    // 注意/r/n前不能有空格
    private static final String CRLF = "\r\n";

    @Resource
    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;


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

    @Override
    public String loadFilterChainDefinitions() {
        StringBuffer sb = new StringBuffer();
        // sb.append(getFixedAuthRule());//固定权限，采用读取配置文件
        return sb.toString();
    }

    // /**
    //  * 从配额文件获取固定权限验证规则串
    //  */
    // private String getFixedAuthRule() {
    //     String fileName = "shiro_base_auth.ini";
    //     ClassPathResource cp = new ClassPathResource(fileName);
    //     INI4j ini = null;
    //     try {
    //         ini = new INI4j(cp.getFile());
    //     } catch (IOException e) {
    //         LoggerUtils.fmtError(getClass(), e, "加载文件出错。file:[%s]", fileName);
    //     }
    //     String section = "base_auth";
    //     Set<String> keys = ini.get(section).keySet();
    //     StringBuffer sb = new StringBuffer();
    //     for (String key : keys) {
    //         String value = ini.get(section, key);
    //         sb.append(key).append(" = ")
    //                 .append(value).append(CRLF);
    //     }
    //
    //     return sb.toString();
    //
    // }

    // 此方法加同步锁
    @Override
    public synchronized void reCreateFilterChains() {
//		ShiroFilterFactoryBean shiroFilterFactoryBean = (ShiroFilterFactoryBean) SpringContextUtil.getBean("shiroFilterFactoryBean");
        AbstractShiroFilter shiroFilter = null;
        try {
            shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean.getObject();
        } catch (Exception e) {
            LoggerUtils.error(getClass(), "getShiroFilter from shiroFilterFactoryBean error!", e);
            throw new RuntimeException("get ShiroFilter from shiroFilterFactoryBean error!");
        }

        PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                .getFilterChainResolver();
        DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver
                .getFilterChainManager();

        // 清空老的权限控制
        manager.getFilterChains().clear();

        shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
        shiroFilterFactoryBean.setFilterChainDefinitions(loadFilterChainDefinitions());
        // 重新构建生成
        Map<String, String> chains = shiroFilterFactoryBean
                .getFilterChainDefinitionMap();
        for (Map.Entry<String, String> entry : chains.entrySet()) {
            String url = entry.getKey();
            String chainDefinition = entry.getValue().trim().replace(" ", "");
            manager.createChain(url, chainDefinition);
        }

    }

    public void setShiroFilterFactoryBean(
            ShiroFilterFactoryBean shiroFilterFactoryBean) {
        this.shiroFilterFactoryBean = shiroFilterFactoryBean;
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
            // Logger(e.getMessage(), e);
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

}
