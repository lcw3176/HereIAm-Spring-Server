package com.joebrooks.hereIam.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.joebrooks.hereIam.service.IJwtService;

@Component
public class AuthInterceptor implements HandlerInterceptor {


	private IJwtService jwtService;
	
	public AuthInterceptor(IJwtService jwtService) {
		this.jwtService = jwtService;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Cookie[] cookies = request.getCookies();
		String token = null;
		
		response.setHeader("pragma", "no-cache");
		response.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
		response.setHeader("Expires", "0");
		
		if(cookies == null) {
			response.sendRedirect("login");
			return false;
		}
		
		for(Cookie c : cookies) {
			if(c.getName().equals("token")) {
				token = c.getValue();
				break;
			}
		}
		
		
		if(token != null && jwtService.isEnable(token)) {
			return true;
		}
		
		response.sendRedirect("login");
		return false;
	}
	
	
}
