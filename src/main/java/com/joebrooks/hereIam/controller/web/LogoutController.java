package com.joebrooks.hereIam.controller.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
@RequestMapping("logout")
public class LogoutController {

	@GetMapping
	public String logout(HttpServletResponse response) {
		Cookie cookie = new Cookie("token", null);
		Cookie nameCookie = new Cookie("name", null);
		cookie.setMaxAge(0);
		nameCookie.setMaxAge(0);
		
		response.addCookie(cookie);
		response.addCookie(nameCookie);
		
		return "redirect:login";
	}
}
