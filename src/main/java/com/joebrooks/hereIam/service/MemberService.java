package com.joebrooks.hereIam.service;

import org.springframework.stereotype.Service;
import com.joebrooks.hereIam.dto.Member;
import com.joebrooks.hereIam.mapper.MemberMapper;

@Service
public class MemberService implements IMemberService {

	private MemberMapper memberDao;

	public MemberService(MemberMapper memberDao) {
		this.memberDao = memberDao;
	}

	@Override
	public boolean register(Member member) {
		try {
			memberDao.register(member);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	@Override
	public boolean isMember(Member member) {
		try {
			return memberDao.isMember(member);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Member selectByEmail(String email) {
		try {
			return memberDao.selectByEmail(email);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Member selectByName(String name) {
		try {
			return memberDao.selectByName(name);
		} catch (Exception e) {
			return null;
		}
	}

}
