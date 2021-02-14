package com.joebrooks.hereIam.handler;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.joebrooks.hereIam.dto.Location;
import com.joebrooks.hereIam.service.ILocationService;
import com.joebrooks.hereIam.service.IRoomService;

@Component
public class SocketHandler extends TextWebSocketHandler {

	private static HashMap<WebSocketSession, String> userMap = new HashMap<WebSocketSession, String>();
	private IRoomService roomService;
	private ILocationService locationService;

	public SocketHandler(IRoomService roomService, ILocationService locationService) {
		this.roomService = roomService;
		this.locationService = locationService;

	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		String userName = session.getUri().toString().split("\\?")[1];
		userName = URLDecoder.decode(userName);
		userMap.put(session, userName);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		String userName = userMap.get(session);
		String roomName = roomService.selectRoomNameByMemberName(userName);

		userMap.remove(session);

		JSONObject json = new JSONObject();
		json.put("type", "remove");
		json.put("userName", userName);

		SendMessage(roomName, json.toJSONString());
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(message.getPayload().toString());
		String type = object.get("type").toString();

		if (type.equals("location")) {
			String location = object.get("location").toString();
			String lat = object.get("lat").toString();
			String lng = object.get("lng").toString();

			String userName = userMap.get(session);
			String roomName = roomService.selectRoomNameByMemberName(userName);
			
			locationService.insertLocation(location, lat, lng, userName);
			List<HashMap<String, Object>> members = roomService.selectMembersByRoomName(roomName);
			JSONArray jarr = new JSONArray();
			
			

			for (HashMap<String, Object> i : members) {
				Location temp = locationService.selectByName(i.get("members").toString());
				JSONObject json = new JSONObject();
				
				json.put("type", "location");
				json.put("members", temp.getName());
				json.put("locations", temp.getPlace());
				json.put("lat", temp.getLat());
				json.put("lng", temp.getLng());
				
				jarr.add(json);

			}

			SendMessage(roomName, jarr.toJSONString());

		}

	}

	private void SendMessage(String roomName, String messageJson) throws Exception {

		List<HashMap<String, Object>> members = roomService.selectMembersByRoomName(roomName);

		for (HashMap<String, Object> i : members) {
			for (WebSocketSession j : userMap.keySet()) {
				if (i.get("members").equals(userMap.get(j))) {
					j.sendMessage(new TextMessage(messageJson));
				}
			}
		}
	}
}
