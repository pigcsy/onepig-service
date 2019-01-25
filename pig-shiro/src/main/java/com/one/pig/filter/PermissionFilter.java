package com.one.pig.filter;//package com.anniu.credit.user.shiro.filter;
//
//import com.alibaba.fastjson.JSON;
//import com.anniu.credit.base.bean.BaseExceptionCode;
//import com.anniu.mid.freework.container.spring.web.ResponseEntity;
//import org.apache.shiro.subject.Subject;
//import org.apache.shiro.web.filter.AccessControlFilter;
//import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//
///**
// * 开发公司：anniu在线工具 <p>
// * 版权所有：© www.anniu.com<p>
// * 博客地址：http://www.anniu.com/blog/  <p>
// * <p>
// * <p>
// * 权限校验 Filter
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
//public class PermissionFilter extends AccessControlFilter {
//    Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
//
//        //先判断带参数的权限判断
//        Subject subject = getSubject(request, response);
//        if (null != mappedValue) {
//            String[] arra = (String[]) mappedValue;
//            for (String permission : arra) {
//                if (subject.isPermitted(permission)) {
//                    return Boolean.TRUE;
//                }
//            }
//        }
//        HttpServletRequest httpRequest = ((HttpServletRequest) request);
//        /**
//         * 此处是改版后，为了兼容项目不需要部署到root下，也可以正常运行，但是权限没设置目前必须到root 的URI，
//         * 原因：如果你把这个项目叫 ShiroDemo，那么路径就是 /ShiroDemo/xxxx.shtml ，那另外一个人使用，又叫Shiro_Demo,那么就要这么控制/Shiro_Demo/xxxx.shtml
//         * 理解了吗？
//         * 所以这里替换了一下，使用根目录开始的URI
//         */
//
//        String uri = httpRequest.getRequestURI();//获取URI
//        String basePath = httpRequest.getContextPath();//获取basePath
//        if (null != uri && uri.startsWith(basePath)) {
//            uri = uri.replaceFirst(basePath, "");
//        }
//        if (subject.isPermitted(uri)) {
//            return Boolean.TRUE;
//        }
//        return Boolean.FALSE;
//    }
//
//    @Override
//    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//        Subject subject = getSubject(request, response);
//        if (!"/".equalsIgnoreCase(((ShiroHttpServletRequest) request).getRequestURI())) {
//            if (null == subject.getPrincipal()) {//表示没有登录，重定向到登录页面
//                // saveRequest(request);
//                response.getWriter().write(JSON.toJSONString(ResponseEntity.failure(BaseExceptionCode.NO_LOGIN.getCode(), BaseExceptionCode.NO_LOGIN.getMsg())));
//                return Boolean.FALSE;
//            }
//        }
//        return Boolean.TRUE;
//    }
//
//}
