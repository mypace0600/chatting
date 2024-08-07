# 실시간 채팅 웹 서비스 프로젝트
## 1. 프로젝트 목표 / 기간
 - 목표 :
   --
   WebSocket, SockJS를 통한 STOMP 형식의 실시간 채팅 웹 서비스 구현 및 배포
 - 기간 : 2024-08-05 ~ 2024-10-27 (12주)
   --
   - 1주 채팅방 이름 설정
   - 2주 채팅방 삭제/나가기
   - 3주 채팅방으로 초대
   - 4주 메세지 삭제
   - 5주 메세지 수정
   - 6주 사진전송 개별 저장
   - 7주 채팅 읽음 처리
   - 8-9주 UI 수정
   - 10주 ec2 배포
   - 11주 cicd 
   - 12주 피드백 리펙토링
## 2. 스펙
 - Java 17
 - SpringBoot 3
 - JPA, QueryDSL
 - PostgreSQL
## 3. 기능
### 1) 회원
- 회원가입 🟢
- 이메일 중복확인 🟢
- 이메일 인증 🟢
- 닉네임 중복확인 🟢
- 닉네임 수정 🟢
- 닉네임 검색 🟢
- 로그인/로그아웃 🟢
### 2) 친구
- 친구 요청 보내기 🟢
- 내가 보낸 친구 요청 목록 조회 🟢
- 내가 받은 친구 요청 목록 조회 🟢
- 친구 요청 수락 🟢
- 친구 차단 🟢
- 전체 친구 목록 조회 🟢
### 3) 채팅방
- 기존 채팅방 존재여부 확인 🟢
- 신규 채팅방 생성 🟢
- 채팅방 이름 설정 🔴
- 채팅방 나가기 🔴
- 채팅방 삭제 🔴
- 채팅방으로 친구 초대하기 🔴
- 안 읽은 메세지 카운트 🔴
### 4) 채팅 메세지
- 채팅 매세지 보내기 🟢
- 채팅 매세지 목록 최신순으로 아래부터 쌓아서 보기 🟢
- 채팅 메세지 읽음 확인 🔴
- 사진 전송시 개별 저장 🔴
- 채팅 메세지 수정 🔴
- 채팅 메세지 삭제 🔴
### 5) UI 개선
- 레퍼런스 디자인 찾기 🔴
- UI 변경 작업 🔴
### 6) 배포
- aws ec2를 통한 배포 🔴
- docker jenkins를 통한 CICD 자동화 🔴

## 6. 추후 계획
- websocket , sockjs 에서 kafka로 전환
- postgresql에서 chatmessage 만 mongodb로 전환
### 참고 : 
- https://woo0doo.tistory.com/38
- https://github.com/hansun-hub/webSocket
- https://1son.tistory.com/416
