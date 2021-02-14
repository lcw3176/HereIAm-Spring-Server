package com.joebrooks.hereIam.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.joebrooks.hereIam.dto.Room;
import com.joebrooks.hereIam.mapper.RoomMapper;

@Service
public class RoomService implements IRoomService {

	private RoomMapper roomDao;

	public RoomService(RoomMapper roomDao) {
		this.roomDao = roomDao;
	}

	@Override
	public boolean register(Room room) {
		try {
			return roomDao.register(room);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<HashMap<String, Object>> selectMembersByRoomName(String name) {
		try {
			return roomDao.selectMembersByRoomName(name);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean isMember(String roomName, String userName) {
		try {
			return roomDao.isMember(roomName, userName);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean isExist(Room room) {
		try {
			return roomDao.isExist(room);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void delete(Room room) {
		try {
			roomDao.delete(room);
		} catch (Exception e) {

		}

	}

	@Override
	public String selectRoomNameByMemberName(String name) {
		try {
			return roomDao.selectRoomNameByMemberName(name);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean hasRoom(String userName) {
		try {
			return roomDao.hasRoom(userName);
		}catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean isEnable(String roomName) {
		try {
			return roomDao.isEnable(roomName);
		} catch(Exception e) {
			return false;
		}
	}


}
