<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@include file="../common/head.jsp"%>
<%@include file="../common/goBackHeader.jsp"%>
<%@include file="../common/sidebar.jsp"%>
<div class="main-content">
    <div class="chat-container">
        <div class="message-list-box" id="messageListBox">
            <ul class="message-list" id="messageList">
            </ul>
        </div>
        <div class="message-input-box">
            <div class="message-input-wrapper">
                <textarea id="messageInput" placeholder="메시지를 입력하세요." rows="3"></textarea>
                <button id="sendButton">전송</button>
            </div>
        </div>
    </div>
</div>
<!-- SockJS 클라이언트 라이브러리 로드 -->
<script src="https://cdn.jsdelivr.net/npm/sockjs-client/dist/sockjs.min.js"></script>
<!-- STOMP 클라이언트 라이브러리 로드 -->
<script src="https://cdn.jsdelivr.net/npm/stompjs/lib/stomp.min.js"></script>
<!-- chat.js 로드 -->
<script src="/js/chat.js"></script>
