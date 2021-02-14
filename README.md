# HereIAm-Spring-Server
그룹 단위 위치 추적 웹사이트&서버
## 기술 스택
* Java, Spring Boot, WebSocket, JWT
* thymeleaf, html, JavaScript, css, Bootstrap
* MySQL, mybatis
* lombok
* WebSocket을 위한 https 적용(발급처: https://www.sslforfree.com)
* kakao 지도 api 사용

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
1. 로그인, 회원가입(SHA-256 암호화)
2. JWT을 통한 인증 서비스(쿠키 이용, 제한 시간 존재)
3. 위치 추적을 위한 방 만들기, 입장하기
4. 웹에서 실시간 위치 추적, 조회를 위한 WebSocket 사용
5. 홈에서 내 방 멤버들 시간별 위치 기록 확인 가능
6. 어플에서 데이터를 받아오기 위한 Mobile 전용 REST API

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
