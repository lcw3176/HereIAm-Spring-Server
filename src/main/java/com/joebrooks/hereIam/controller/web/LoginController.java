package com.joebrooks.hereIam.controller.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.joebrooks.hereIam.common.EncryptSha256;
import com.joebrooks.hereIam.dto.Member;
import com.joebrooks.hereIam.service.IJwtService;
import com.joebrooks.hereIam.service.IMemberService;

@Controller
@RequestMapping("login")
public class LoginController {

	private IJwtService jwtService;
	private IMemberService memberService;

	public LoginController(IJwtService jwtService, IMemberService memberService) {
		this.jwtService = jwtService;
		this.memberService = memberService;
	}

	@GetMapping
	public String loginForm(@CookieValue(value = "token", defaultValue = "0") String token, Member member) {

		if (jwtService.isEnable(token)) {
			return "redirect:home";
		}

		return "page/login";
	}

	@PostMapping
	public String requestLogin(HttpServletResponse response, Model model, Member member) {
		member.setPw(EncryptSha256.encrypt(member.getPw()));

		if (!memberService.isMember(member)) {
			model.addAttribute("loginErr", "error");
			return "page/login";
		}

		String token = jwtService.createToken(member.getEmail(), 60 * 60 * 1000);
		String name = memberService.selectByEmail(member.getEmail()).getName();

		Cookie cookie = new Cookie("token", token);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(60 * 60);

		Cookie nameCookie = new Cookie("name", name);
		nameCookie.setMaxAge(60 * 60);

		response.addCookie(cookie);
		response.addCookie(nameCookie);

		return "redirect:home";
	}
}
