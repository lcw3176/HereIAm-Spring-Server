package com.joebrooks.hereIam.controller.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.joebrooks.hereIam.common.EncryptSha256;
import com.joebrooks.hereIam.dto.Member;
import com.joebrooks.hereIam.service.IMemberService;

@Configuration
@RequestMapping("join")
public class RegisterController {

	private IMemberService memberService;

	public RegisterController(IMemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping
	public String registerForm(Member member) {
		return "page/register";
	}

	@PostMapping
	public String requestRegist(Model model, Member member) {

		if (!memberService.isMember(member)) {
			 member.setPw(EncryptSha256.encrypt(member.getPw())); 
			
			if (memberService.register(member)) {
				return "redirect:login";
			}
		}

		model.addAttribute("registerErr", "error");
		return "page/register";
	}
}
