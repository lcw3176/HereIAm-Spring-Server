package com.joebrooks.hereIam.controller.mobile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joebrooks.hereIam.dto.Location;
import com.joebrooks.hereIam.service.ILocationService;

@RestController
@RequestMapping("/m/location")
public class MLocationController {

	private ILocationService locationService;

	public MLocationController(ILocationService locationService) {
		this.locationService = locationService;
	}

	@PostMapping
	public String registerLocation(@RequestParam("name") String name, @RequestParam("lat") String lat,
			@RequestParam("lng") String lng, @RequestParam("place") String place) {
		Date date_now = new Date(System.currentTimeMillis());
		System.out.println(name + "::" + place + "=>" + date_now);
		locationService.insertLocation(place, lat, lng, name);
		
		return "good";
	}
	

	@GetMapping("/{memberName}/{date}/{fromtime}/{totime}")
	public JSONObject requestLocation(@PathVariable("memberName") String memberName, @PathVariable("date") String date,
			@PathVariable("fromtime") String fromtime, @PathVariable("totime") String totime) {
		fromtime = date + " " + fromtime;
		totime = date + " " + totime;
		List<HashMap<String, Object>> location = locationService.selectLocations(memberName, fromtime, totime);
		JSONArray jarr = new JSONArray();
		JSONObject value = new JSONObject();

		if (location.isEmpty()) {
			JSONObject json = new JSONObject();
			json.put("time", "null");
			jarr.add(json);
			value.put("data", jarr);

			return value;
		}

		for (HashMap<String, Object> loc : location) {
			JSONObject json = new JSONObject();

			json.put("time", loc.get("time").toString());
			json.put("lat", loc.get("lat"));
			json.put("lng", loc.get("lng"));

			jarr.add(json);
		}

		value.put("data", jarr);

		return value;
	}
}
