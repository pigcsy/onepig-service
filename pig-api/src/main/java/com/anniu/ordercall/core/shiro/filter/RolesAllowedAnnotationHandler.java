package com.anniu.ordercall.core.shiro.filter;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;

import javax.annotation.security.RolesAllowed;
import java.lang.annotation.Annotation;
import java.util.Arrays;

public class RolesAllowedAnnotationHandler extends AuthorizingAnnotationHandler {


    /**
     * 构造函数
     *
     * @param
     */
    public RolesAllowedAnnotationHandler() {
        super(RolesAllowed.class);
    }

    @Override
    public void assertAuthorized(Annotation a) throws AuthorizationException {
        RolesAllowed rrAnnotation = (RolesAllowed) a;
        String[] roles = rrAnnotation.value();
        getSubject().checkRoles(Arrays.asList(roles));
        return;
    }

}