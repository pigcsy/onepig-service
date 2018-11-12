package com.one.pig.core.shiro;

import com.anniu.mid.freework.container.spring.web.exception.ApiException;
import com.anniu.ordercall.bean.dto.user.UserDto;
import com.anniu.ordercall.core.shiro.service.SessionService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;

import static com.anniu.ordercall.core.exception.BizExceptionEnum.NEED_LOGIN;

public class RequestArgumentResolver implements HandlerMethodArgumentResolver {
    @Resource
    private SessionService sessionService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return UserDto.class.isAssignableFrom(parameter.getParameterType()) ||
                String.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object ret = null;
        if (UserDto.class.isAssignableFrom(parameter.getParameterType())) {
            ret = sessionService.getLoginObject();
            if (ret == null && (parameter.getMethod().getAnnotation(RequiresUser.class) != null
                    || parameter.getMethod().getAnnotation(RequiresRoles.class) != null
                    || parameter.getMethod().getAnnotation(RequiresPermissions.class) != null
                    || parameter.getMethod().getAnnotation(RequiresAuthentication.class) != null
            )) {
                throw new ApiException(NEED_LOGIN.getCode(), NEED_LOGIN.getMessage());
            }
        } else if (String.class.isAssignableFrom(parameter.getParameterType())) {
            ret = webRequest.getParameter(parameter.getParameterName());
            ret = ret == null ? null : ((String) ret).trim();
        }
        return ret;
    }
}
