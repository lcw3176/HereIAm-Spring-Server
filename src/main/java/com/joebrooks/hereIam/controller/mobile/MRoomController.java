package com.joebrooks.hereIam.controller.mobile;

import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joebrooks.hereIam.common.EncryptSha256;
import com.joebrooks.hereIam.dto.Location;
import com.joebrooks.hereIam.dto.Member;
import com.joebrooks.hereIam.dto.Room;
import com.joebrooks.hereIam.service.ILocationService;
import com.joebrooks.hereIam.service.IMemberService;
import com.joebrooks.hereIam.service.IRoomService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/m/room")
@AllArgsConstructor
public class MRoomController {

	private IRoomService roomService;
	private ILocationService locationService;

	@GetMapping("/members/{roomName}/{userName}")
	public JSONObject MembersData(@PathVariable("roomName") String roomName,
			@PathVariable("userName") String userName) {
		JSONObject json = new JSONObject();
		JSONArray jarr = new JSONArray();

		if (!roomService.isMember(roomName, userName)) {
			JSONObject value = new JSONObject();

			value.put("error", "failed");
			jarr.add(value);

			json.put("data", jarr);

			return json;
		}

		List<HashMap<String, Object>> members = roomService.selectMembersByRoomName(roomName);

		for (HashMap<String, Object> mem : members) {
			Location temp = locationService.selectByName(mem.get("members").toString());
			JSONObject value = new JSONObject();
			
			if(temp == null) {
				value.put("name", mem.get("members"));
				value.put("place", null);
				value.put("lat", null);
				value.put("lng", null);
				value.put("time", null);
				
				jarr.add(value);
				continue;
			}
			
			

			value.put("name", temp.getName());
			value.put("place", temp.getPlace());
			value.put("lat", temp.getLat());
			value.put("lng", temp.getLng());
			value.put("time", temp.getTime().toString());

			jarr.add(value);
		}
		
		if(jarr.size() == 0) {
			JSONObject value = new JSONObject();
			value.put("error", "failed");
			jarr.add(value);
		}

		json.put("data", jarr);

		return json;
	}

	@PostMapping("/checkin")
	public JSONObject EnterRoom(@RequestParam("name") String roomName, 
			@RequestParam("member") String userName,
			@RequestParam("pw") String pw) {
		Room room = new Room();
		room.setName(roomName);
		room.setMember(userName);
		room.setPw(EncryptSha256.encrypt(pw));

		JSONObject json = new JSONObject();

		if (!roomService.isExist(room)) {
			json.put("data", "failed");

			return json;
		}

		roomService.register(room);
		json.put("data", "success");

		return json;
	}

	@DeleteMapping("/checkout/{roomName}/{userName}")
	public JSONObject DeleteRoom(@PathVariable("roomName") String roomName, @PathVariable("userName") String userName) {
		JSONObject json = new JSONObject();
		Room room = new Room();
		room.setName(roomName);
		room.setMember(userName);

		roomService.delete(room);

		json.put("data", "success");

		return json;
	}

}
