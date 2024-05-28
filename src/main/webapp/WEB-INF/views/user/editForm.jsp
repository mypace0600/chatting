<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@include file="../common/head.jsp"%>
<link rel="stylesheet" type="text/css" href="/css/user.css">
</head>
<body>
<div class="container">
    <div class="join-box">
        <div>회원정보 수정 화면입니다.</div>
        <form id="editForm">
            <div class="form-group">
                <label for="email">이메일 주소</label>
                <span>${user.email}</span>
            </div>
            <div class="form-group">
                <label for="password">비밀번호</label>
                <input type="password" name="password" id="password" class="form-control" placeholder="비밀번호를 입력해주세요" value="${user.password}"/>
            </div>
            <div class="form-group">
                <label for="nickName">닉네임</label>
                <input type="text" name="nickName" id="nickName" class="form-control" placeholder="닉네임을 입력해주세요"  value="${user.nickName}"/>
                <input type="hidden" name="nicknameDuplicateCheck" id="nicknameDuplicateCheck" value="false"/>
                <button type="button" class="btn" id="nicknameDuplicateCheckBtn">닉네임 중복 확인</button>
            </div>
            <button type="button" class="btn btn-primary" id="editBtn">저장</button>
            <button type="button" class="btn btn-secondary" onclick="history.back()">취소</button>
        </form>
    </div>
</div>
</body>
</html>
<script src="/js/user.js"></script>
