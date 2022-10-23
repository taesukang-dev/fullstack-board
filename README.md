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
    - redis
		- live chat service : 10.19

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

| URI | RequestBody | ResponseBody |
| --- | ----------- | ------------ |
| Post <br /> /join | {usename:"", password: ""} | {resultCode: "", result: {}} |
| Post <br /> /login | {usename:"", password: ""} | {resultCode: "", result: {token : ""}} |

<br />

- <b>posts</b>

| URI | RequestBody | ResponseBody |
| --- | ----------- | ------------ |
| Get <br /> /list | {usename:"", password: ""} | {resultCode: "", result: {[{id: "", title: "", content: "", username: "", registerAt: ""}, ]}} |
| Get <br /> /post/{postId} | /postId | {resultCode: "", result: {{id: "", title: "", content: "", username: "", registerAt: ""}}} |
| Write <br /> /write | {title: "", content: ""} | {resultCode: "", result: {{id: "", title: "", content: "", username: "", registerAt: ""}}} |
| Put <br /> /{postId}/update | /postId<br /> {title: "", content: ""} | {resultCode: "", result: {{id: "", title: "", content: "", username: "", registerAt: ""}}} |
| Delete <br /> /{postId}/delete | /postd | {resultCode: "", result: {}} |

<br />

- <b>comments</b>

| URI | RequestBody | ResponseBody |
| --- | ----------- | ------------ |
| Get <br /> /{postId} | /postId | {resultCode: "", result: {{id: "", content: "", username: "", child, [""], registerAt: ""}}} |
| Post <br /> /{postId} | /postId <br /> {content : ""} | result: {{id: "", content: "", username: "", registerAt: ""}}} |
| Post <br /> /{postId}/{parentId} | /postId/parentId <br /> {content : ""} | result: {{id: "", content: "", username: "", registerAt: ""}}} |
| Delete <br /> /{postId}/{commentId} | /postId/commentId | {resultCode: "", result: {}} |

<br />

## 구현사항

- 로컬환경에서 구동되는 아주 간단한 게시판을 구현한다.
	- 게시글 CRUD
		- Spring data JPA가 아닌 Entity Manager를 사용하는 순수 JPA를 사용
		- paging
		- join fetch를 통한 N + 1 문제 회피
		- react query를 통해 ux 향상 도모
		- styled components를 통해 공통된 사항은 재사용하게 한다.
		- api는 axios instance를 생성하여 presetational component와 분리한다.
	- 댓글 CRD
		- CascadeType.ALL 을 활용하여 대댓글의 삭제나 생성도 자동으로 하게 한다. 
		- 대댓글 CRD
			- 대댓글은 Maximum recursion 한도 내에서 무한으로 생성 가능
				- 대댓글의 대댓글의 대댓글...
			- indent size를 통해 댓글과 대댓글 구별 가능
	- 회원 CR
	- Authentication
		- Jwt
		- spring security 사용
			- 최신버전으로 websecurityconfigureradapter 를 extends 하지 않는 방식으로 적용
	- 알람 CD
		- 실시간 알림
	- 서버에서 생기는 Error는 직접 handling 할 수 있도록 custom 하여 사용
		- GlobalControllerAdvice에서 handling
	- DDD를 차용한 Domain 내에서의 logic 설계
	- 서버에서 나가는 response는 정해진 규격을 맞추기 위해 Response 객체 생성하여 활용
	- java, javascript 모두 가독성을 높이기 위해 메소드 체이닝을 적극 활용
