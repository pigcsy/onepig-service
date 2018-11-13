package com.one.pig.core.shiro.filter;


import com.one.pig.core.shiro.token.ShiroUser;
import com.one.pig.core.shiro.token.manager.TokenManager;
import com.one.pig.core.util.common.LoggerUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 判断登录
 *
 * @author csy
 * @version 2016年6月2日 <br/>
 */
public class LoginFilter extends AccessControlFilter {
    final static Class<LoginFilter> CLASS = LoginFilter.class;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        ShiroUser token = TokenManager.getToken();
        if (null != token || isLoginRequest(request, response)) {// && isEnabled()
            return Boolean.TRUE;
        }
        if (ShiroFilterUtils.isHttp(request)) {
            Map<String, String> resultMap = new HashMap<String, String>();
            LoggerUtils.debug(getClass(), "当前用户没有登录！");
            resultMap.put("login_status", "300");
            resultMap.put("message", "\u5F53\u524D\u7528\u6237\u6CA1\u6709\u767B\u5F55\uFF01");//当前用户没有登录！
            ShiroFilterUtils.out(response, resultMap);
        }
        return Boolean.FALSE;

    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //保存Request和Response 到登录后的链接
        saveRequestAndRedirectToLogin(request, response);
        return Boolean.FALSE;
    }


}
