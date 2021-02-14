package com.joebrooks.hereIam.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.joebrooks.hereIam.dto.Room;

@Mapper
public interface RoomMapper {

	// 방 등록
	boolean register(Room room);

	// 방 삭제
	void delete(Room room);

	// 해당 방의 모든 멤버들 정보 가져오기
	List<HashMap<String, Object>> selectMembersByRoomName(String name);

	// 멤버 이름으로 방 이름 가져오기
	String selectRoomNameByMemberName(String name);

	// 해당 방의 멤버인지 확인
	boolean isMember(String roomName, String userName);

	// 존재하는 방인지 체크, 유저가 방 검색시 사용.
	boolean isExist(Room room); 
	
	// 소속된 방이 있는지 체크, 소속 되어 있다면 다른 방 입장이나 새로운 방 모집을 막는다.
	boolean hasRoom(String userName);  
	
	// 방 정원 체크, 최대 정원 4명.
	boolean isEnable(String roomName); 

}
