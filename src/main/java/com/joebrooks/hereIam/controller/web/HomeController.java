package com.joebrooks.hereIam.controller.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.joebrooks.hereIam.dto.Location;
import com.joebrooks.hereIam.service.ILocationService;
import com.joebrooks.hereIam.service.IRoomService;

@Controller
@RequestMapping(value = { "home", "/" })
public class HomeController {

	private IRoomService roomService;
	private ILocationService locationService;

	public HomeController(ILocationService locationService, IRoomService roomService) {
		this.locationService = locationService;
		this.roomService = roomService;
	}

	@GetMapping("/")
	public String redirect() {
		return "redirect:home";
	}

	@GetMapping("home")
	public String Home(Model model, @CookieValue("name") String name,
			@RequestParam(name = "userName", defaultValue = "none") String requestUserName,
			@RequestParam(name = "time", defaultValue = "none") String requestTime) {

		String roomName = roomService.selectRoomNameByMemberName(name);
		List<Location> locations = null;
		List<HashMap<String, Object>> detailInfo = null;

		if (roomName != null) {
			List<HashMap<String, Object>> members = roomService.selectMembersByRoomName(roomName);
			locations = new ArrayList<Location>();

			for (HashMap<String, Object> i : members) {
				Location temp = locationService.selectByName(i.get("members").toString());

				locations.add(temp);
			}

			if (!requestUserName.equals("none")) {
				detailInfo = locationService.selectLocations(requestUserName, requestTime + " 00:00:00", requestTime + " 23:59:59");
				model.addAttribute("requestTime", requestTime);
			}
		}

		model.addAttribute("name", name);
		model.addAttribute("locationInfo", locations);
		model.addAttribute("detailInfo", detailInfo);
		model.addAttribute("logTarget", requestUserName);

		
		return "page/home";
	}
}
