package com.one.pig.filter;//package com.anniu.credit.user.shiro.filter;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.anniu.credit.base.bean.BaseExceptionCode;
//import com.anniu.credit.user.shiro.session.SessionStatus;
//import com.anniu.mid.freework.container.spring.web.ResponseEntity;
//import org.apache.shiro.session.Session;
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.web.filter.AccessControlFilter;
//import org.apache.shiro.web.util.WebUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.Map;
//
//import static com.anniu.credit.user.shiro.session.RedisSessionDAO.SESSION_STATUS;
//
///**
// * 开发公司：anniu在线工具 <p>
// * 版权所有：© www.anniu.com<p>
// * 博客地址：http://www.anniu.com/blog/  <p>
// * <p>
// * <p>
// * 判断是否踢出
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
//public class SimpleAuthFilter extends AccessControlFilter {
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
//        HttpServletRequest httpRequest = ((HttpServletRequest) request);
//        String url = httpRequest.getRequestURI();
//        if (url.startsWith("/")) {
//            return Boolean.TRUE;
//        }
//        Subject subject = getSubject(request, response);
//        Session session = subject.getSession();
//        Map<String, String> resultMap = new HashMap<String, String>();
//        SessionStatus sessionStatus = (SessionStatus) session.getAttribute(SESSION_STATUS);
//        if (null != sessionStatus && !sessionStatus.isOnlineStatus()) {
//            response.getWriter().write(JSON.toJSONString(ResponseEntity.failure(BaseExceptionCode.KICK_OUT.getCode(), BaseExceptionCode.KICK_OUT.getMsg())));
//            return Boolean.FALSE;
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
//        /**
//         * 保存Request，用来保存当前Request，然后登录后可以跳转到当前浏览的页面。
//         * 比如：
//         * 我要访问一个URL地址，/admin/index.html，这个页面是要登录。然后要跳转到登录页面，但是登录后要跳转回来到/admin/index.html这个地址，怎么办？
//         * 传统的解决方法是变成/user/login.shtml?redirectUrl=/admin/index.html。
//         * shiro的解决办法不是这样的。需要：<code>WebUtils.getSavedRequest(request);</code>
//         * 							 然后：{@link UserLoginController.submitLogin(...)}中的<code>String url = WebUtils.getSavedRequest(request).getRequestUrl();</code>
//         * 如果还有问题，请咨询我。
//         */
//        WebUtils.saveRequest(request);
//        //再重定向
//        WebUtils.issueRedirect(request, response, "/open/kickedOut.shtml");
//        return false;
//    }
//
//    private void out(ServletResponse hresponse, Map<String, String> resultMap)
//            throws IOException {
//        hresponse.setCharacterEncoding("UTF-8");
//        PrintWriter out = hresponse.getWriter();
//        out.println(JSONObject.toJSON(resultMap).toString());
//        out.flush();
//        out.close();
//    }
//}
