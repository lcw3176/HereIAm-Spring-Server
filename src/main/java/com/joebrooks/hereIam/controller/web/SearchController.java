package com.joebrooks.hereIam.controller.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.joebrooks.hereIam.common.EncryptSha256;
import com.joebrooks.hereIam.dto.Room;

@Configuration
@RequestMapping("search")
public class SearchController {

	@GetMapping
	public String searchForm(Model model, Room room, @CookieValue("name") String name,
			@RequestParam(name = "result", defaultValue = "None") String result) {

		if (!result.equals("None")) {
			model.addAttribute("searchErr", result);
		}

		model.addAttribute("name", name);

		return "page/search";
	}

	@PostMapping
	public String requestSearch(Model model, Room room, @CookieValue("name") String name,
			RedirectAttributes attributes) {

		room.setPw(EncryptSha256.encrypt(room.getPw()));
		Map<String, Room> map = new HashMap<String, Room>();
		map.put("room", room);

		attributes.addFlashAttribute("room", map);

		return "redirect:room";
	}
}
