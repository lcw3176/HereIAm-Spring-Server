package com.joebrooks.hereIam.dto;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("room")
public class Room {

	private String name;
	private String pw;
	private String member;
}
