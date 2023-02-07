# <img src="http://1.234.189.11/gitlogo/teemowhite.png">
[FE GitHub](https://github.com/DongJu-Na/scouter-frontend)<br/>
롤 전적검색 사이트 [Scouter](http://1.234.189.11/) api 프로젝트 입니다<br/>
`2023.01.01 ~ 2023.02.05` 동안 `spring Boot`를 이용해 구현했습니다.


이 프로젝트를 통해 이루고자 한 목표는 <br/>
`Spring에서 제공하는 프레임워클 직접 설정하고 사용하며 Spring에 대한 이해도를 높임` <br/>
`가상 Cloud를 직접 세팅하여 클라우드 컴퓨팅에 대한 이해와 경험을 늘리고` <br/>
`CI/CD 파이프라인을 직접 설계해보기 였습니다.` <br/>

# 📚 목차
* [사용 기술](#-사용-기술)
* [구현 기능](#-구현-기능)
* [API 명세서](#-API-명세서)
* [트러블슈팅](#-트러블슈팅)

# 🕹 사용 기술
### 📌 Backend
|기술|버전|
|----|----|
|Java|17|
|Spring Boot|3.0.1|
|Spring Security|3.0.1|
|Spring Data Jpa|3.0.1|
|Spring Restdocs Mockmvc|3.0.1|
|asciidoctor|3.3.2|
|querydsl|1.0.10|
|flyway|9.10.2|
|JSON Web Token|0.11.2|
|MariaDB|10.3|

# 🎢 구현 기능
* 롤 API
  * 아이디 조회 [아이콘, 고유id, puuid 레벨]
  * 전적 조회 [최근 10경기]
  * 리그조회 [티어, 포인트, 승, 패]
  * 랭킹조회 [롤 전체랭킹]
* 게시판 기능
  * 모든 게시글 조회
  * 게시글 검색 
  * 게시글 작성 [회원]
  * 게시글 수정 [회원, 게시글 작성자]
  * 게시글 삭제 [회원, 게시글 작성자]
  * 게시글 답글 작성 [회원]
* 댓글 기능
  * 댓글 조회
  * 댓글 작성 [회원]
  * 댓글 수정 [회원, 댓글 작성자]
  * 댓글 삭제 [회원, 댓글 작성자]
* 회원 기능
  * 회원가입
  * 로그인/로그아웃

# 🤙🏻 API 명세서
HTTP 메서드를 통해 행위를 명시할 수 있도록 RESTful 방식으로 설계했습니다. <br/><br/>

[회원](http://1.234.189.11/docs/user-guide.html)<br/>
[게시판](http://1.234.189.11/docs/board-guide.html)<br/>
[댓글](http://1.234.189.11/docs/Reply-guide.html)<br/>

# 👾 트러블슈팅
### 회원 인증 및 인가 기능 구현 (Spring Security + JWT)

퍼블릭 사이트
