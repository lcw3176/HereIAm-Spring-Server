package com.joebrooks.hereIam.controller.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.joebrooks.hereIam.common.EncryptSha256;
import com.joebrooks.hereIam.dto.Room;
import com.joebrooks.hereIam.service.IRoomService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("recruit")
@AllArgsConstructor
public class RecruitController {

	private IRoomService roomService;


	@GetMapping
	public String recruitForm(Model model, Room room, @CookieValue("name") String name) {
		model.addAttribute("name", name);
		return "page/recruit";
	}

	@PostMapping
	public String registerRoom(Model model, RedirectAttributes attributes, @CookieValue("name") String name, Room room) {
		if(roomService.hasRoom(name)) {
			model.addAttribute("name", name);
			model.addAttribute("error", "belongtoRoom");
			return "page/recruit";
		}
		
		room.setMember(name);
		room.setPw(EncryptSha256.encrypt(room.getPw()));

		if (roomService.register(room)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("room", room);

			attributes.addFlashAttribute("room", map);
			return "redirect:room";
		}

		return "page/recruit";
	}
}
