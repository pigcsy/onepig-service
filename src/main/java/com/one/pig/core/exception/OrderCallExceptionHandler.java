/*
 * Created by zhangzxiang91@gmail.com on 2017/10/10.
 */
package com.one.pig.core.exception;

import com.anniu.mid.freework.container.spring.web.exception.ApiException;
import com.anniu.mid.freework.container.spring.web.exception.GlobalExceptionHandler;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.session.UnknownSessionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

import static com.anniu.ordercall.core.exception.BizExceptionEnum.*;

/**
 * 全局异常处理器
 *
 * @author zhangzxiang91@gmail.com
 * @date 2017/10/10.
 */
public class OrderCallExceptionHandler extends GlobalExceptionHandler {


    @Override
    public ModelAndView getModelAndView(Exception e, HttpServletRequest request, HandlerMethod handler) {
        if (e instanceof UnauthenticatedException || e instanceof UnknownSessionException) {
            e = new ApiException(LOGIN_TIME_OUT.getCode(), LOGIN_TIME_OUT.getMessage());

        } else if (e instanceof UnauthorizedException) {
            e = new ApiException(LOGIN_FAIL.getCode(), LOGIN_FAIL.getMessage());
        } else if (e instanceof AuthorizationException) {
            e = new ApiException(NO_POWER_GET.getCode(), NO_POWER_GET.getMessage());
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            if (bindException.getFieldErrorCount() > 0) {
                for (FieldError fieldError : bindException.getFieldErrors()) {
                    e = new ApiException(NO_POWER_GET.getCode(), NO_POWER_GET.getMessage());
                    break;
                }
            }
        } else if (e instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> cvs = constraintViolationException.getConstraintViolations();
            if (CollectionUtils.isNotEmpty(cvs)) {
                for (ConstraintViolation<?> cv : cvs) {
                    e = new ApiException(INPUT_ERR.getCode(), INPUT_ERR.getMessage());
                    break;
                }
            }
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            e = new ApiException(POST_OR_GET_ERR.getCode(), POST_OR_GET_ERR.getMessage());
        }

        return super.getModelAndView(e, request, handler);
    }

    @Override
    protected boolean isOutputJSON(HttpServletRequest request, HandlerMethod handler) {
        if (super.isOutputJSON(request, handler)) {
            return true;
        }

        if (!StringUtils.containsIgnoreCase(request.getHeader(HttpHeaders.ACCEPT), MediaType.TEXT_HTML_VALUE)) {
            return true;
        } else {
            return false;
        }
    }
}
