package com.joebrooks.hereIam.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.joebrooks.hereIam.dto.Location;
import com.joebrooks.hereIam.mapper.LocationMapper;

@Service
public class LocationService implements ILocationService {

	private LocationMapper locationDao;

	public LocationService(LocationMapper locationDao) {
		this.locationDao = locationDao;
	}

	@Override
	public boolean insertLocation(String place, String lat, String lng, String name) {
		try {
			locationDao.insertLocation(place, lat, lng, name);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Location selectByName(String name) {
		try {
			return locationDao.selectByName(name);
		} catch (Exception e) {
			return null;
		}
	}

//	@Override
//	public List<HashMap<String, Object>> selectAllByName(String name) {
//		try {
//			return locationDao.selectAllByName(name);
//		} catch (Exception e) {
//			return null;
//		}
//	}

	@Override
	public List<HashMap<String, Object>> selectLocations(String name, String fromtime, String totime) {
		try {
			return locationDao.selectLocations(name, fromtime, totime);
		} catch(Exception e) {
			return null;
		}
	}

}
