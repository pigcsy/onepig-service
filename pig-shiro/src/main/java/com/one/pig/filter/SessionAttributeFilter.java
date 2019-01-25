package com.one.pig.filter;

import com.anniu.credit.user.service.UserService;
import com.anniu.mid.freework.container.context.ContainerContext;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class SessionAttributeFilter implements Filter {

   @Override
   public void init(FilterConfig filterConfig) throws ServletException {
   }

   @Override
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       UserService userService = ContainerContext.get().getContext().getBean(UserService.class);
       String sessionId = userService.getSessionId(request);
       Object mobile = userService.getAttribute("sessionPrincipal", sessionId);
       if (mobile != null) {
           request.setAttribute("sessionPrincipal", mobile);
       }
       chain.doFilter(request, response);
   }

   @Override
   public void destroy() {
   }
}
