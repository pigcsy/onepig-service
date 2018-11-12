package com.anniu.ordercall.core.shiro.token.manager;


import com.anniu.mid.freework.container.spring.web.exception.ApiException;
import com.anniu.ordercall.core.shiro.session.CustomSessionManager;
import com.anniu.ordercall.core.shiro.token.ShiroRealm;
import com.anniu.ordercall.core.shiro.token.ShiroUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.anniu.ordercall.core.exception.BizExceptionEnum.ACCOUNT_OR_PASSWORD_ERR;

/**
 * <p>
 * <p>
 * <p>
 * <p>
 * 区分　责任人　日期　　　　说明<br/>
 * 创建　csy　 　<br/>
 * <p>
 *
 * @author csy
 * @version Shiro管理下的Token工具类
 */
@Component
public class TokenManager {
    //用户登录管理
    @Autowired
    static
    ShiroRealm shiroRealm;

    //用户session管理
    @Autowired
    static
    CustomSessionManager customSessionManager;

    /**
     * 获取当前登录的用户User对象
     *
     * @return
     */
    public static ShiroUser getToken() {
        ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        return shiroUser;
    }


    /**
     * 获取当前用户的Session
     *
     * @return
     */
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    /**
     * 获取当前用户NAME
     *
     * @return
     */
    public static String getNickname() {
        return getToken().getName();
    }

    /**
     * 获取当前用户ID
     *
     * @return
     */
    public static Long getUserId() {
        return getToken() == null ? null : (long) getToken().getUserId();
    }

    /**
     * 把值放入到当前登录用户的Session里
     *
     * @param key
     * @param value
     */
    public static void setVal2Session(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * 从当前登录用户的Session里取值
     *
     * @param key
     * @return
     */
    public static Object getVal2Session(Object key) {
        return getSession().getAttribute(key);
    }

    /**
     * 获取验证码，获取一次后删除
     *
     * @return
     */
    public static String getYZM() {
        String code = (String) getSession().getAttribute("CODE");
        getSession().removeAttribute("CODE");
        return code;
    }


    /**
     * 登录
     *
     * @param usernamePasswordToken
     * @return
     */
    public static ShiroUser login(UsernamePasswordToken usernamePasswordToken) {
        try {
            SecurityUtils.getSubject().login(usernamePasswordToken);
        } catch (Exception e) {
            throw new ApiException(ACCOUNT_OR_PASSWORD_ERR.getCode(), ACCOUNT_OR_PASSWORD_ERR.getMessage());
        }
        return getToken();
    }


    /**
     * 判断是否登录
     *
     * @return
     */
    public static boolean isLogin() {
        return null != SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 退出登录
     */
    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    /**
     * 根据UserIds 	清空权限信息。
     *
     * @param userIds 用户ID
     */
    public static void clearUserAuthByUserId(Long... userIds) {

        if (null == userIds || userIds.length == 0) return;
        List<SimplePrincipalCollection> result = customSessionManager.getSimplePrincipalCollectionByUserId(userIds);

        for (SimplePrincipalCollection simplePrincipalCollection : result) {
            shiroRealm.clearCachedAuthorizationInfo(simplePrincipalCollection);
        }
    }

    /**
     * 方法重载
     *
     * @param userIds
     */
    public static void clearUserAuthByUserId(List<Long> userIds) {
        if (null == userIds || userIds.size() == 0) {
            return;
        }
        clearUserAuthByUserId(userIds.toArray(new Long[0]));
    }

    /**
     * 清空当前用户权限信息。
     * 目的：为了在判断权限的时候，再次会再次 <code>doGetAuthorizationInfo(...)  </code>方法。
     * ps：	当然你可以手动调用  <code> doGetAuthorizationInfo(...)  </code>方法。
     * 这里只是说明下这个逻辑，当你清空了权限，<code> doGetAuthorizationInfo(...)  </code>就会被再次调用。
     */
    public void clearNowUserAuth() {
        /**
         * 这里需要获取到shrio.xml 配置文件中，对Realm的实例化对象。才能调用到 Realm 父类的方法。
         */
        /**
         * 获取当前系统的Realm的实例化对象，方法一（通过 @link org.apache.shiro.web.mgt.DefaultWebSecurityManager 或者它的实现子类的{Collection<Realm> getRealms()}方法获取）。
         * 获取到的时候是一个集合。Collection<Realm>
         RealmSecurityManager securityManager =
         (RealmSecurityManager) SecurityUtils.getSecurityManager();
         SampleRealm SampleRealm = (SampleRealm)securityManager.getRealms().iterator().next();
         */
        /**
         * 方法二、通过ApplicationContext 从Spring容器里获取实列化对象。
         */
        shiroRealm.clearCachedAuthorizationInfo();
        /**
         * 当然还有很多直接或者间接的方法，此处不纠结。
         */
    }
}
