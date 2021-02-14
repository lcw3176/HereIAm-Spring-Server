package com.joebrooks.hereIam.service;

public interface IJwtService {
	String createToken(String subject, long mills);
	
	String createMobileToken(String subject);
	
	String getSubject(String token);
	
	boolean isEnable(String token);
}
