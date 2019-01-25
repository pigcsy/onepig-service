package com.one.pig.filter;//package com.anniu.credit.user.shiro.filter;
//
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.anniu.credit.base.bean.BaseExceptionCode;
//import com.anniu.credit.redis.RedisCacheManager;
//import com.anniu.credit.user.shiro.session.RedisSessionDAO;
//import com.anniu.credit.user.shiro.token.manager.TokenManager;
//import com.anniu.mid.freework.container.spring.web.ResponseEntity;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.web.filter.AccessControlFilter;
//import org.apache.shiro.web.util.WebUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.Serializable;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
///**
// * 开发公司：anniu在线工具 <p>
// * 版权所有：© www.anniu.com<p>
// * 博客地址：http://www.anniu.com/blog/  <p>
// * <p>
// * <p>
// * 相同帐号登录控制
// * <p>
// * <p>
// * <p>
// * 区分　责任人　日期　　　　说明<br/>
// * 创建　csy　2016年6月2日 　<br/>
// *
// * @author csy
// * @version 1.0, 2016年6月2日 <br/>
// * @email so@anniu.com
// */
//@SuppressWarnings({"unchecked", "static-access"})
//public class KickoutSessionFilter extends AccessControlFilter {
//
//    //在线用户
//    final static String ONLINE_USER = KickoutSessionFilter.class.getCanonicalName() + "_online_user";
//    //踢出状态，true标示踢出
//    final static String KICKOUT_STATUS = KickoutSessionFilter.class.getCanonicalName() + "_kickout_status";
//    //静态注入
//    static String kickoutUrl;
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//    //session获取
//    @Autowired
//    private RedisCacheManager redisCacheManager;
//
//    public static String getKickoutUrl() {
//        return kickoutUrl;
//    }
//
//    public static void setKickoutUrl(String kickoutUrl) {
//        KickoutSessionFilter.kickoutUrl = kickoutUrl;
//    }
//
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
//        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//        HttpServletRequest httpRequest = ((HttpServletRequest) request);
//        String url = httpRequest.getRequestURI();
//        Subject subject = getSubject(request, response);
//        //如果是相关目录 or 如果没有登录 就直接return true
//        if (url.startsWith("/index.html") || (!subject.isAuthenticated() && !subject.isRemembered())) {
//            return Boolean.TRUE;
//        }
//        Session session = subject.getSession();
//        Serializable sessionId = session.getId();
//        /**
//         * 判断是否已经踢出
//         */
//        Boolean marker = (Boolean) session.getAttribute(KICKOUT_STATUS);
//        if (null != marker && marker) {
//            response.getWriter().write(JSON.toJSONString(ResponseEntity.failure(BaseExceptionCode.KICK_OUT.getCode(), BaseExceptionCode.KICK_OUT.getMsg())));
//            return Boolean.FALSE;
//        }
//
//
//        //从缓存获取用户-Session信息 <UserId,SessionId>
//        LinkedHashMap<Long, Serializable> infoMap = redisCacheManager.get(ONLINE_USER, LinkedHashMap.class);
//        //如果不存在，创建一个新的
//        infoMap = null == infoMap ? new LinkedHashMap<Long, Serializable>() : infoMap;
//
//        //获取tokenId
//        Long userId = TokenManager.getUserId();
//
//        //如果已经包含当前Session，并且是同一个用户，跳过。
//        if (infoMap.containsKey(userId) && infoMap.containsValue(sessionId)) {
//            //更新存储到缓存1个小时（这个时间最好和session的有效期一致或者大于session的有效期）
//            redisCacheManager.put(ONLINE_USER, infoMap, 86400 * 30);
//            return Boolean.TRUE;
//        }
//        //如果用户相同，Session不相同，那么就要处理了
//        /**
//         * 如果用户Id相同,Session不相同
//         * 1.获取到原来的session，并且标记为踢出。
//         * 2.继续走
//         */
//        if (infoMap.containsKey(userId) && !infoMap.containsValue(sessionId)) {
//            Serializable oldSessionId = infoMap.get(userId);
//            Session oldSession = redisSessionDAO.readSession(oldSessionId);
//            if (null != oldSession) {
//                //标记session已经踢出
//                oldSession.setAttribute(KICKOUT_STATUS, Boolean.TRUE);
//                redisSessionDAO.saveSession(oldSession);//更新session
//                logger.info("kickout old session success,oldId[%s]", oldSessionId);
//            } else {
//                redisSessionDAO.delete((Session) oldSessionId);
//                infoMap.remove(userId);
//                //存储到缓存1个小时（这个时间最好和session的有效期一致或者大于session的有效期）
//                redisCacheManager.put(ONLINE_USER, infoMap, 86400 * 30);
//            }
//            return Boolean.TRUE;
//        }
//
//        if (!infoMap.containsKey(userId) && !infoMap.containsValue(sessionId)) {
//            infoMap.put(userId, sessionId);
//            //存储到缓存1个小时（这个时间最好和session的有效期一致或者大于session的有效期）
//            redisCacheManager.put(ONLINE_USER, infoMap, 86400 * 30);
//        }
//        return Boolean.TRUE;
//    }
//
//    @Override
//    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//
//        //先退出
//        Subject subject = getSubject(request, response);
//        subject.logout();
//        WebUtils.getSavedRequest(request);
//        //再重定向
//        WebUtils.issueRedirect(request, response, kickoutUrl);
//        return false;
//    }
//
//    private void out(ServletResponse hresponse, Map<String, String> resultMap)
//            throws IOException {
//        try {
//            hresponse.setCharacterEncoding("UTF-8");
//            PrintWriter out = hresponse.getWriter();
//            out.println(JSONObject.toJSON(resultMap).toString());
//            out.flush();
//            out.close();
//        } catch (Exception e) {
//            logger.info("KickoutSessionFilter.class 输出JSON异常，可以忽略。");
//        }
//    }
//
//
//}
