package com.joebrooks.hereIam.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.joebrooks.hereIam.interceptor.AuthInterceptor;


@Configuration
public class WebConfig implements WebMvcConfigurer{

	private AuthInterceptor jwtInterceptor;
	
	public WebConfig(AuthInterceptor jwtInterceptor) {
		this.jwtInterceptor = jwtInterceptor;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		List<String> url = new ArrayList<String>();
		url.add("/login");
		url.add("/join");
		url.add("/m/*");
		
		registry.addInterceptor(jwtInterceptor)
				.addPathPatterns("/*")
				.excludePathPatterns(url);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	      registry.addResourceHandler("/resource/**").setCachePeriod(0);
	}
}
