var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	mapOption = {
		center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
		level: 10
		// 지도의 확대 레벨 
	};

// 주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
var watchId;


// HTML5의 geolocation으로 사용할 수 있는지 확인합니다 
if (navigator.geolocation) {

	// GeoLocation을 이용해서 접속 위치를 얻어옵니다
	watchId = navigator.geolocation.watchPosition(function(position) {
		var lat = position.coords.latitude, // 위도
			lon = position.coords.longitude; // 경도
		
		searchDetailAddrFromCoords(
			lat,
			lon,
			function(result, status) {
				if (status === kakao.maps.services.Status.OK) {
					var detailAddr = result[0].address.address_name;

					// 소켓 위치 전송
					sendLocation(detailAddr, lat, lon);

				}
			});
	});
} else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다

	var locPosition = new kakao.maps.LatLng(33.450701, 126.570667), message = 'geolocation을 사용할수 없어요..'

	displayMarker(locPosition, message);
}


var clusterer = new kakao.maps.MarkerClusterer({
	map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체 
	averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정 
	minLevel: 10 // 클러스터 할 최소 지도 레벨 
});

var markerObj = new Object();
markerObj.data = [];


// 지도에 마커와 인포윈도우를 표시하는 함수입니다
function displayMarker(locPosition, message, messageId) {

	// 마커를 생성합니다
	var marker = new kakao.maps.Marker({
		map: map,
		position: locPosition
	});
	

	clusterer.addMarker(marker);
	
	var iwContent = message, // 인포윈도우에 표시할 내용
		iwRemoveable = true;

	// 인포윈도우를 생성합니다
	var infowindow = new kakao.maps.InfoWindow({
		content: iwContent,
		removable: iwRemoveable
	});

	markerObj.data.push({user:messageId.replace('message', ''), marker:marker, window:infowindow, });
	
	
	// 인포윈도우를 마커위에 표시합니다 
	infowindow.open(map, marker);
	document.getElementById(messageId).innerHTML = messageId.replace('message', '');

	// 지도 중심좌표를 접속위치로 변경합니다
	map.setCenter(locPosition);
}

function searchDetailAddrFromCoords(lat, lng, callback) {
	// 좌표로 법정동 상세 주소 정보를 요청합니다
	geocoder.coord2Address(lng, lat, callback);
}