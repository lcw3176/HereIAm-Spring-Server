var userName = document.cookie.split('=')[1];
var socket;
var isOpen = false;

socket = new WebSocket("wss://www.joebrooks.tk/roomSocket?" + userName);

socket.onopen = function(e) {
	isOpen = true;
};

socket.onmessage = function(event) {
	var data = JSON.parse(event.data);

	if (Array.isArray(data) || data.length >= 2) {


		for (var i = 0; i < markerObj.data.length; i++) {
			clusterer.removeMarker(markerObj.data[i].marker);
			markerObj.data[i].window.close();

		}


		for (var i = 0; i < data.length; i++) {

			var tag = document.getElementById(data[i].members);

			if (tag != null) {

				tag.parentNode.removeChild(tag);
			}
			
			var content = document.getElementById('addContent');

			var border = document.createElement('div');
			border.className = "col-xl-3 col-md-6 mb-4";
			border.id = data[i].members;

			var card = document.createElement('div');
			card.className = "card border-left-success shadow h-100 py-2";

			var body = document.createElement('div');
			body.className = "card-body";

			var row = document.createElement('div');
			row.className = "row no-gutters align-items-center";

			var col = document.createElement('div');
			col.className = "col mr-2";

			var memberName = document.createElement('div');
			memberName.className = "text-xs font-weight-bold text-primary text-uppercase mb-1";
			memberName.innerHTML = data[i].members;

			var location = document.createElement('div');
			location.className = "h5 mb-0 font-weight-bold text-gray-800";
			location.innerHTML = data[i].locations;

			content.appendChild(border);
			border.appendChild(card);
			card.appendChild(body);
			body.appendChild(row);
			row.appendChild(col);
			col.appendChild(memberName);
			col.appendChild(location);

			var messageId = data[i].members + "message";

			var locPosition = new kakao.maps.LatLng(data[i].lat, data[i].lng), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
				message = '<div id=' + messageId + ' style="height:30px; margin:3px;"></div>'; // 인포윈도우에 표시될 내용입니다

			// 마커와 인포윈도우를 표시합니다
			displayMarker(locPosition, message, messageId);

		}

	}

	if (data.type == "remove") {

		var tag = document.getElementById(data.userName);
		tag.parentNode.removeChild(tag);

		for (var i = 0; i < markerObj.data.length; i++) {
			if (markerObj.data[i].user == data.userName) {
				clusterer.removeMarker(markerObj.data[i].marker);
				markerObj.data[i].window.close();
			}

		}

	}

};

socket.onclose = function(event) {
	navigator.geolocation.clearWatch(watchId);
};

socket.onerror = function(error) {

};


function sendLocation(location, lat, lng) {

	var message = new Object();
	message.type = "location";
	message.location = location;
	message.lat = lat;
	message.lng = lng;


	if (isOpen) {
		socket.send(JSON.stringify(message));
	} else {
		setTimeout(function() {
			socket.send(JSON.stringify(message));
		}, 1000);
	}

}