package com.anniu.ordercall.core.shiro.filter;

import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;

public class RoleAllowsAnnotationMethodInterceptor extends AuthorizingAnnotationMethodInterceptor {


    public RoleAllowsAnnotationMethodInterceptor() {
        super(new RolesAllowedAnnotationHandler());
    }

    public RoleAllowsAnnotationMethodInterceptor(AnnotationResolver resolver) {
        super(new RolesAllowedAnnotationHandler(), resolver);
    }


}
