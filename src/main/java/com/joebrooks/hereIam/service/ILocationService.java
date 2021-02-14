package com.joebrooks.hereIam.service;

import java.util.HashMap;
import java.util.List;

import com.joebrooks.hereIam.dto.Location;

public interface ILocationService {
	boolean insertLocation(String place, String lat, String lng, String name);
	
	Location selectByName(String name);
	
//	List<HashMap<String, Object>> selectAllByName(String name);
	
	List<HashMap<String, Object>> selectLocations(String name, String fromtime, String totime);
}
