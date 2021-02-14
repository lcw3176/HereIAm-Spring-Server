package com.joebrooks.hereIam.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.joebrooks.hereIam.dto.Location;

@Mapper
public interface LocationMapper {

	// 유저 위치 기록하기
	boolean insertLocation(String place, String lat, String lng, String name);

	// 유저 이름으로 마지막으로 기록된 위치정보 1개 가져오기, 홈 화면에 접속 기록 보여줄 때 사용
	Location selectByName(String name);

	// 유저 위치 정보 시간별로 가져오기
	List<HashMap<String, Object>> selectLocations(String name, String fromtime, String totime);
}
