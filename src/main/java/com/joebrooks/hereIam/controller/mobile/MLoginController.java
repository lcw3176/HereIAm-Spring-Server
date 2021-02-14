package com.joebrooks.hereIam.controller.mobile;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joebrooks.hereIam.common.EncryptSha256;
import com.joebrooks.hereIam.dto.Member;
import com.joebrooks.hereIam.service.IJwtService;
import com.joebrooks.hereIam.service.IMemberService;

@RestController
@RequestMapping("/m/login")
public class MLoginController {

	private IMemberService memberService;

	public MLoginController(IMemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping("/{email}/{pw}")
	public JSONObject requestToken(@PathVariable("email") String email, @PathVariable("pw") String pw) {
		
		Member member = new Member();
		member.setEmail(email);		
		member.setPw(EncryptSha256.encrypt(pw));
		JSONObject json = new JSONObject();

		if (!memberService.isMember(member)) {
			json.put("result", "notMember");

			return json;
		}
		 
		json.put("result", "success");

		return json;

	}

}
