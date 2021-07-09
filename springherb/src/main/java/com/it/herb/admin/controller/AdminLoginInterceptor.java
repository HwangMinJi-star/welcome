package com.it.herb.admin.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AdminLoginInterceptor implements HandlerInterceptor{
	private static final Logger logger=LoggerFactory.getLogger(AdminLoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String adminUserid=(String) request.getSession().getAttribute("adminUserid");
		logger.info("admin-preHandle() 호출, adminUserid={}",adminUserid);
		
		if(adminUserid==null || adminUserid.isEmpty()) {
			request.setAttribute("msg", "먼저 관리자 로그인하세요");
			request.setAttribute("url", "/admin/login/adminLogin");
			
			RequestDispatcher dispatcher=request.getRequestDispatcher("/WEB-INF/views/common/message.jsp");
			dispatcher.forward(request, response);
			
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
		logger.info("admin-postHandle() 호출");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.info("admin-afterCompletion() 호출");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
}
