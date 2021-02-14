package com.joebrooks.hereIam.controller.mobile;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joebrooks.hereIam.dto.Location;
import com.joebrooks.hereIam.service.ILocationService;
import com.joebrooks.hereIam.service.IMemberService;
import com.joebrooks.hereIam.service.IRoomService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/m/members")
@AllArgsConstructor
public class MMembersController {

	private IRoomService roomService;
	private ILocationService locationService;
	private IMemberService memberService;

	@GetMapping("/getmemberlocation/{memberName}")
	public JSONObject membersLocationData(@PathVariable("memberName") String memberName) {
		JSONObject json = new JSONObject();

		Location location = locationService.selectByName(memberName);
		json.put("name", location.getName());
		json.put("lat", location.getLat());
		json.put("lng", location.getLng());
		json.put("place", location.getPlace());

		return json;
	}

	@GetMapping("/getmyinfomation/{email}")
	public JSONObject requestMembers(@PathVariable("email") String email) {
		JSONObject json = new JSONObject();

		String userName = memberService.selectByEmail(email).getName();
		String roomName = roomService.selectRoomNameByMemberName(userName);

		json.put("name", userName);
		json.put("room", roomName);

		return json;

	}
}
