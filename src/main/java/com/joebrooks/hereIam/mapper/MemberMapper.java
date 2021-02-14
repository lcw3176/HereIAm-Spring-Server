package com.joebrooks.hereIam.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.joebrooks.hereIam.dto.Member;

@Mapper
public interface MemberMapper {
	
	// 멤버 등록하기
	void register(Member member) throws Exception;
	
	// 이메일로 멤버 정보 가져오기
	Member selectByEmail(String email) throws Exception;
	
	// 이름으로 멤버 정보 가져오기
	Member selectByName(String name) throws Exception;
	
	// 멤버인지 확인, 로그인 시 사용
	boolean isMember(Member member) throws Exception;
}
