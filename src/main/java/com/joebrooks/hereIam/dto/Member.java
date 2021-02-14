package com.joebrooks.hereIam.dto;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("member")
public class Member {

	private String name;
	private String email;
	private String pw;
	
}
