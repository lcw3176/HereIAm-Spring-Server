package com.joebrooks.hereIam.service;

import com.joebrooks.hereIam.dto.Member;

public interface IMemberService {
	boolean register(Member member);

	boolean isMember(Member member);

	Member selectByEmail(String email);

	Member selectByName(String name);

}
