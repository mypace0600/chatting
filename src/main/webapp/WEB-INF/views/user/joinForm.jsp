<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@include file="../common/head.jsp"%>
<link rel="stylesheet" type="text/css" href="/css/user.css">
</head>
<body>
<div class="container">
    <div class="join-box">
        <div>회원가입 화면입니다.</div>
        <form id="joinForm">
            <div class="form-group">
                <label for="email">이메일 주소</label>
                <input type="email" name="email" id="email" class="form-control" placeholder="이메일을 입력해주세요"/>
                <input type="hidden" name="emailDuplicateCheck" id="emailDuplicateCheck" value="false"/>
                <button type="button" class="btn" id="emailDuplicateCheckBtn">이메일 중복 확인</button>
                <button type="button" class="btn" id="emailVerifyCodeSendBtn" style="display:none;">이메일 인증번호 발송</button>
            </div>
            <div class="form-group" id="emailVerificationGroup" style="display:none;">
                <label for="emailVerificationCode">이메일 인증번호</label>
                <input type="text" name="emailVerificationCode" id="emailVerificationCode" class="form-control" placeholder="인증번호를 입력해주세요"/>
                <input type="hidden" name="emailVerifyCheck" id="emailVerifyCheck" value="false"/>
                <button type="button" class="btn" id="verifyEmailCheckBtn">인증번호 확인</button>
            </div>
            <div class="form-group">
                <label for="password">비밀번호</label>
                <input type="password" name="password" id="password" class="form-control" placeholder="비밀번호를 입력해주세요"/>
            </div>
            <div class="form-group">
                <label for="nickName">닉네임</label>
                <input type="text" name="nickName" id="nickName" class="form-control" placeholder="닉네임을 입력해주세요"/>
                <input type="hidden" name="nicknameDuplicateCheck" id="nicknameDuplicateCheck" value="false"/>
                <button type="button" class="btn" id="nicknameDuplicateCheckBtn">닉네임 중복 확인</button>
            </div>
            <button type="button" class="btn btn-primary" id="joinBtn">회원가입</button>
            <button type="button" class="btn btn-secondary" onclick="history.back()">뒤로가기</button>
        </form>
    </div>
</div>
</body>
</html>
<script src="/js/user.js"></script>
