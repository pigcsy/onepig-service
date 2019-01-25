package com.one.pig.filter;

import com.alibaba.fastjson.JSON;
import com.anniu.mid.freework.container.spring.web.ResponseEntity;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class AuthFilter extends AccessControlFilter {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		Subject subject = getSubject(request, response);
		return subject.getPrincipal() != null;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		try {
			response.setContentType("application/json; charset=utf-8");
			response.getWriter().write(JSON.toJSONString(ResponseEntity.failure(00,"")));
			response.getWriter().flush();
		} catch (IOException ex) {
			logger.error("写入未登录请求结果出错：", ex);
		}
		return false;
	}
}
