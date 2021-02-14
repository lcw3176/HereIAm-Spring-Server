package com.joebrooks.hereIam.service;

import java.util.HashMap;
import java.util.List;

import com.joebrooks.hereIam.dto.Room;

public interface IRoomService {
	boolean register(Room room);
	
	void delete(Room room);
	
	List<HashMap<String,Object>> selectMembersByRoomName(String name);
	
	String selectRoomNameByMemberName(String name);
	
	boolean isMember(String roomName, String userName);
	
	boolean isExist(Room room);
	
	boolean hasRoom(String userName);
	
	boolean isEnable(String roomName);
}
