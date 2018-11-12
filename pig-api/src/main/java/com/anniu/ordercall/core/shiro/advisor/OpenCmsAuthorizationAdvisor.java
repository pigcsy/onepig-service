package com.anniu.ordercall.core.shiro.advisor;

import com.anniu.ordercall.core.base.BaseController;
import com.anniu.ordercall.core.shiro.filter.OpenCmsAnnotationsAuthorizingMethodInterceptor;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class OpenCmsAuthorizationAdvisor extends AuthorizationAttributeSourceAdvisor {

    //权限注解
    private static final Class<? extends Annotation>[] AUTHZ_ANNOTATION_CLASSES =
            new Class[]{
                    RequiresPermissions.class, RequiresRoles.class,
                    RequiresUser.class, RequiresGuest.class, RequiresAuthentication.class
            };

    //web注解
    private static final Class<? extends Annotation>[] WEB_ANNOTATION_CLASSES =
            new Class[]{
                    RequestMapping.class
            };


    /**
     * Create a new AuthorizationAttributeSourceAdvisor.
     */
    public OpenCmsAuthorizationAdvisor() {
        setAdvice(new OpenCmsAnnotationsAuthorizingMethodInterceptor());
    }


    /**
     * 匹配带有注解的方法
     */
    @Override
    public boolean matches(Method method, Class targetClass) {
        boolean flag = super.matches(method, targetClass);

        //如果方法上没有权限注解，尝试获取类上的默认权限注解
        if (!flag && isAuthzAnnotationPresent(targetClass) && isWebAnnotationPresent(method)) {
            flag = true;
        }

        return flag;
    }


    private boolean isAuthzAnnotationPresent(Class<BaseController> clazz) {
        for (Class<? extends Annotation> annClass : AUTHZ_ANNOTATION_CLASSES) {
            Annotation a = AnnotationUtils.findAnnotation(clazz, annClass);
            if (a != null) {
                return true;
            }
        }
        return false;
    }


    private boolean isAuthzAnnotationPresent(Method method) {
        for (Class<? extends Annotation> annClass : AUTHZ_ANNOTATION_CLASSES) {
            Annotation a = AnnotationUtils.findAnnotation(method, annClass);
            if (a != null) {
                return true;
            }
        }
        return false;
    }


    private boolean isWebAnnotationPresent(Method method) {
        for (Class<? extends Annotation> annClass : WEB_ANNOTATION_CLASSES) {
            Annotation a = AnnotationUtils.findAnnotation(method, annClass);
            if (a != null) {
                return true;
            }
        }
        return false;
    }


}
