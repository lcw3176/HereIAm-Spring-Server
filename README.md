# HereIAm-Spring-Server
그룹 단위 위치 추적 웹사이트&서버
## 기술 스택
* Java, Spring Boot, WebSocket, JWT
* thymeleaf, html, JavaScript, css, Bootstrap
* MySQL, mybatis
* lombok, kakao 지도 api
* WebSocket을 위한 https 적용(발급처: https://www.sslforfree.com)

## 데이터베이스
* location 테이블
<pre>
<code>
create table user (
    name varchar(20) not null Primary key,
    email varchar(30) not null,
    pw varchar(64) not null
)
</code>
</pre>

* room 테이블
<pre>
<code>
create table room (
    members varchar(20),
    email varchar(40),
    pw varchar(64),
    FOREIGN KEY(members) REFERENCES user(name)
    ON DELETE CASCADE ON UPDATE CASCADE
)
</code>
</pre>

* location 테이블
<pre>
<code>
create table location (
    lat varchar(20),
    lng varchar(20),
    name varchar(20),
    place varchar(20),
    time timestamp,
    FOREIGN KEY(name) REFERENCES user(name)
    ON DELETE CASCADE ON UPDATE CASCADE
)
</code>
</pre>

## 구현 기능
* 로그인, 회원가입(SHA-256 암호화)
* JWT을 통한 인증 서비스(쿠키 이용, 제한 시간 존재)
* 위치 추적을 위한 방 만들기, 입장하기
* 웹에서 실시간 위치 추적, 조회를 위한 WebSocket 사용
* 홈에서 내 방 멤버들 시간별 위치 기록 확인 가능
* 어플에서 데이터를 받아오기 위한 Mobile 전용 REST API

## 한계
* 모바일 환경 타겟으로 만들었으나, 실제 사용시 불편함
    - 브라우저를 계속 띄워놔야해서 접속 유지 어려움
    - 포커즈가 벗어나거나, 폰 sleep시 추적 힘듬 

## 개선
* 어플로 만들어서 위치 기록을 지속시킴 ([HereIAm_App](https://github.com/lcw3176/HereIAm-Xamarin-Client))
* 웹단에서는 가입과 조회를, 모바일에서는 기록을 주력으로 함


## URL 구조
### WEB
|Task|Method|Path|Parameter|
|-----------|-----|--------|----|
|메인 화면|GET|/home||
|멤버 위치 기록 조회|GET|/home|userName, time|
|로그인 화면|GET|/login||
|로그인 요청|POST|/login|member 객체|
|회원가입 화면|GET|/join||
|회원가입 요청|POST|/join|member 객체|
|로그아웃|GET|/logout||
|내 방 입장하기|GET|/myroom||
|방 만들기 화면|GET|/recruit||
|방 생성하기|POST|/recruit|room 객체|
|방 입장하기(검색, 생성)|GET|/room|room 객체|
|방 검색 화면|GET|/search||
|result에 따라 에러 메세지 생성|GET|/search|result|
|암호화 후 room으로 redirect|POST|/search|room 객체|
|에러 페이지|GET|/error||

### MOBILE
|Task|Method|Path|Parameter|
|---------|-----|--------|----|
|위치 정보 등록|POST|/m/location|name, lat, lng, place|
|위치 정보 조회|GET|/m/location/{memberName}/{date}/{fromtime}/{totime}||
|로그인|GET|/m/login/{email}/{pw}||
|멤버 위치 정보 조회|GET|/m/members/location/{memberName}||
|내 정보 조회|GET|/m/members/myinfo/{email}||
|방 멤버들 정보 조회|GET|/m/room/members/{roomName}/{userName}||
|방 입장|POST|/m/room/checkin|roomName, userName, pw||
|방 퇴장|DELETE|/m/room/checkout/{roomName}/{userName}||

## 작동 사진
#### 메인 홈, 기록 열람
<div>
<img src="https://user-images.githubusercontent.com/59993347/108018113-211f5080-705a-11eb-9102-d9fd90cc959a.jpg" width=250>

<img src="https://user-images.githubusercontent.com/59993347/108018116-22507d80-705a-11eb-8701-68c37451e49b.jpg" width=250>

</div>

#### 방 만들기, 방 검색, 실시간 멤버 표시
<div>
<img src="https://user-images.githubusercontent.com/59993347/108018120-241a4100-705a-11eb-98ad-f42a8b765ccb.jpg" width=250>

<img src="https://user-images.githubusercontent.com/59993347/108018123-24b2d780-705a-11eb-8747-069ad851ac0d.jpg" width=250>

<img src="https://user-images.githubusercontent.com/59993347/108018124-254b6e00-705a-11eb-999d-f838cb1f9e46.jpg" width=250>

</div>

#### 로그인, 회원 가입
<div>
<img src="https://user-images.githubusercontent.com/59993347/108018128-27153180-705a-11eb-915c-42c036072f32.jpg" width=250>

<img src="https://user-images.githubusercontent.com/59993347/108018129-27adc800-705a-11eb-9196-c104ef89d4dc.jpg" width=250>

</div>