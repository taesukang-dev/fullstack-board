# React와 Spring으로 만든 게시판

## 작업 기한

- 2022.10.04 ~ 2022.10.11
	- 10.08 ~ 10.09 frontend 관련기술 조사기간

<br />

## Tech Stack

- Frontend
	- Node 16+
	- React 18.2.0
	- Redux-toolkit 1.8.6
	- React-Query 4.10.3
	- axios 1.1.2
	- styled-components 5.3.6
	- Framer-motion 7.5.3
- Backend
	- Java 11+
	- gradle lts
	- Spring-boot 2.7.4
	- Jpa lts
	- Spring Security lts
	- MySQL 8+
	- Jwt
		- refresh token : 10.17
	- WebSocket
	- Stomp
	- SSE
	- redis
		- live chat service : 10.19
		- SSE alarm : 10.25

<br />

## Architecture

- Frontend
	- Atomic design pattern

- Backend
	- RESTful API
	- Domain Driven Design
	- Test Driven Development

<br />

## Entity Relationship Diagram

<img src="./board_erd.png">

<br />

## API Table

<br />

- <b>user</b>

| URI                 | RequestBody | ResponseBody                                              | 설명           |
|---------------------| ----------- |-----------------------------------------------------------|--------------|
| Get <br /> /        | {usename:"", password: ""} | {resultCode: "", result: {id : "", username: "", role: ""}} | 유저 정보        |
| Post <br /> /join   | {usename:"", password: ""} | {resultCode: "", result: {id : "", username: "", role: ""}}| 회원 가입        |
| Post <br /> /login  | {usename:"", password: ""} | {resultCode: "", result: {atk : "", rtk: ""}}             | 로그인          |
| Get <br /> /reissue | {usename:"", password: ""} | {resultCode: "", result: {atk : ""}}             | 리프레쉬을 통한 재발급 |

<br />

- <b>posts</b>

| URI                            | RequestBody | ResponseBody | 설명           |
|--------------------------------| ----------- | ------------ |--------------|
| Get <br /> /list               | {usename:"", password: ""} | {resultCode: "", result: {[{id: "", title: "", content: "", username: "", registerAt: ""}, ]}} | 게시글 리스트 조회   |
| Get <br /> /post/{postId}      | /postId | {resultCode: "", result: {{id: "", title: "", content: "", username: "", registerAt: ""}}} | 게시글 세부 정보 조회 |
| Post <br /> /write             | {title: "", content: ""} | {resultCode: "", result: {{id: "", title: "", content: "", username: "", registerAt: ""}}} | 게시글 작성       |
| Put <br /> /{postId}/update    | /postId<br /> {title: "", content: ""} | {resultCode: "", result: {{id: "", title: "", content: "", username: "", registerAt: ""}}} | 게시글 수정       |
| Delete <br /> /{postId}/delete | /postd | {resultCode: "", result: {}} | 게시글 삭제       |

<br />

- <b>comments</b>

| URI | RequestBody | ResponseBody | 설명 |
| --- | ----------- | ------------ | --- |
| Get <br /> /{postId} | /postId | {resultCode: "", result: {{id: "", content: "", username: "", child, [""], registerAt: ""}}} | 댓글 조회 |
| Post <br /> /{postId} | /postId <br /> {content : ""} | result: {{id: "", content: "", username: "", registerAt: ""}}} | 댓글 작성 |
| Post <br /> /{postId}/{parentId} | /postId/parentId <br /> {content : ""} | result: {{id: "", content: "", username: "", registerAt: ""}}} | 대댓글 작성 |
| Delete <br /> /{postId}/{commentId} | /postId/commentId | {resultCode: "", result: {}} | 댓글 삭제 |

<br />

- <b>chatroom</b>

| URI                       | RequestBody | ResponseBody                                        | 설명 |
|---------------------------| ----------- |-----------------------------------------------------| --- |
| Get <br /> /room          | | {resultCode: "", result: {{roomId: "", name: ""}}}  | 채팅방 조회 |
| Post <br /> /rooms        | | {resultCode: "", result: {[{roomId: "", name: ""}]}} | 채팅방 생성 |
| Get <br /> /room/{roomId} | |{resultCode: "", result: {[{roomId: "", name: ""}]}}| 채팅방 접속 |

<br />

- <b>chat</b>

| URI                       | RequestBody | ResponseBody                                        | 설명               |
|---------------------------| ----------- |-----------------------------------------------------|------------------|
| MessageMapping <br /> /chat/message | {type : "", roomId : "", sender : "", message : ""} | {type : "", roomId : "", sender : "", message : ""} | socket 통신을 위한 접속 | 

<br />

- <b>alarm</b>

| URI                       | RequestBody | ResponseBody                                        | 설명               |
|---------------------------| ----------- |-----------------------------------------------------|------------------|
| Get <br /> /subscribe | AuthenticationPrincipal | {SseEmitter} | Sse 구독 |
| Post <br /> / | {AuthenticationPrincipal : "", receivedUsername : "", postId: ""} | | 알람 생성 |
| Get <br /> / | AuthenticationPrincipal | [{id : "", alarmType : "", {postId: "", fromUsername: ""}, registeredAt : ""}] | 알람 조회 |
| Delete <br /> / | {AuthenticationPrincipal, alarmId: ""} | | 조회한 알람 soft delete |

## 구현사항

- 로컬환경에서 구동되는 아주 간단한 게시판을 구현한다.

- 게시글 CRUD
	- Spring data JPA가 아닌 Entity Manager를 사용하는 순수 JPA를 사용
	- paging
	- join fetch를 통한 N + 1 문제 회피
	- react query를 통해 ux 향상 도모
	- styled components를 통해 공통된 사항은 재사용하게 한다.
	- api는 axios instance를 생성하여 presetational component와 분리한다.

<br />

- 댓글 CRD
	- CascadeType.ALL 을 활용하여 대댓글의 삭제나 생성도 자동으로 하게 한다.
	- 대댓글 CRD
		- 대댓글은 Maximum recursion 한도 내에서 무한으로 생성 가능
			- 대댓글의 대댓글의 대댓글...
		- indent size를 통해 댓글간 구별 가능

<br />

- 회원 CR
- Authentication
	- Jwt
		- Refresh token을 Redis에 캐싱하여 관리함으로 보안 강화
	- spring security 사용
		- 최신버전으로 websecurityconfigureradapter 를 extends 하지 않는 방식으로 적용

<br />

- 채팅방 CR
	- Websocket과 Stomp를 사용한 채팅방
	- Redis를 사용하여 후에 있을 확장을 고려하였다.

<br />

- 알람 CRD
	- 타 회원이 내 글에 댓글을 달면 알람이 실시간으로 확인됨
	- SSE를 통한 알람 기능
	- Soft delete를 통해 회원이 조회를 마쳐도 DB내부 혹은 백오피스에서 조회 가능
	- 마찬가지로 Redis를 사용해 확장성을 고려

<br />

- 서버에서 생기는 Error는 직접 handling 할 수 있도록 custom 하여 사용
	- GlobalControllerAdvice에서 handling
- DDD를 차용한 Domain 내에서의 logic 설계
- 서버에서 나가는 Response는 정해진 규격을 맞추기 위해 Response 객체 생성하여 활용
- java, javascript 모두 가독성을 높이기 위해 메소드 체이닝을 적극 활용