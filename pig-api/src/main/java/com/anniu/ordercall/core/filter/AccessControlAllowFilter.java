package com.anniu.ordercall.core.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class AccessControlAllowFilter implements Filter {
    @Bean
    public Filter filter() {
        return new AccessControlAllowFilter();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        if (StringUtils.isBlank(resp.getHeader("Access-Control-Allow-Origin"))) {
            resp.addHeader("Access-Control-Allow-Origin", "*");
        }
        if (StringUtils.isBlank(resp.getHeader("Access-Control-Allow-Methods"))) {
            resp.addHeader("Access-Control-Allow-Methods", "HEAD, POST, GET, OPTIONS");
        }
        if (StringUtils.isBlank(resp.getHeader("Access-Control-Allow-Headers"))) {
            resp.addHeader("Access-Control-Allow-Headers", "accept, origin,  x-requested-with, content-type, sessionId");
        }
        if (StringUtils.isBlank(resp.getHeader("Access-Control-Allow-Credentials"))) {
            resp.addHeader("Access-Control-Allow-Credentials", "true");
        }
        if (StringUtils.isBlank(resp.getHeader("Access-Control-Expose-Headers"))) {
            resp.addHeader("Access-Control-Expose-Headers", "sessionId");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
