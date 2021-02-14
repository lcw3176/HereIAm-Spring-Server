package com.joebrooks.hereIam.controller.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.joebrooks.hereIam.dto.Room;
import com.joebrooks.hereIam.service.IRoomService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("room")
@AllArgsConstructor
public class RoomController {

	private IRoomService roomService;

	@GetMapping
	public String roomForm(HttpServletRequest request, @CookieValue("name") String name, Model model,
			RedirectAttributes redirectAttributes) {

		if(roomService.hasRoom(name)) {
			redirectAttributes.addAttribute("result", "belongtoRoom");
			return "redirect:search";
		}
		
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

		if (flashMap == null) {
			redirectAttributes.addAttribute("result", "notValidate");
			return "redirect:search";
		}
			

		Map<String, Room> roomMap = (Map<String, Room>) flashMap.get("room");
		Room room = roomMap.get("room");

		if (!roomService.isExist(room)) {
			redirectAttributes.addAttribute("result", "notValidate");
			return "redirect:search";
		}
		
		if(!roomService.isEnable(room.getName())) {
			redirectAttributes.addAttribute("result", "fullCount");
			return "redirect:search";
		}

		if (!roomService.isMember(room.getName(), name)) {
			room.setMember(name);
			roomService.register(room);
		}

		model.addAttribute("name", name);
		model.addAttribute("roomName", room.getName());

		return "page/room";
	}

}
