package com.joebrooks.hereIam.dto;

import java.sql.Timestamp;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("location")
public class Location {
	private String lat;
	private String lng;
	private String name;
	private String place;
	private Timestamp time;
	private Long nowTime;
}
