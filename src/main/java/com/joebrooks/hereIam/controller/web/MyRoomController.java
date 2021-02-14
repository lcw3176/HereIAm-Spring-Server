package com.joebrooks.hereIam.controller.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.joebrooks.hereIam.service.IRoomService;

@Configuration
@RequestMapping("myroom")
public class MyRoomController {
	private IRoomService roomService;

	public MyRoomController(IRoomService roomService) {
		this.roomService = roomService;
	}

	@GetMapping
	public String requestSearch(@CookieValue("name") String name, Model model) {
		String roomName = roomService.selectRoomNameByMemberName(name);

		if(roomName == null) {
			return "page/home";
		}
		
		model.addAttribute("name", name);
		model.addAttribute("roomName", roomName);

		return "page/room";
	}
}
