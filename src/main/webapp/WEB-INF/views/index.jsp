<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>메인 화면</title>
    <link rel="stylesheet" type="text/css" href="css/common.css">
</head>
<body>
<div class="container">
    <sec:authorize access="isAnonymous()">
        <div class="auth-buttons">
            <button><a href="/auth/loginForm">로그인</a></button>
            <button><a href="/auth/joinForm">회원가입</a></button>
        </div>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <div class="main-content">
            <div class="search-bar">
                <input type="text" placeholder="친구 검색">
                <button>검색</button>
            </div>
            <div class="friends-list">
                <ul>
                    <li>친구 1</li>
                    <li>친구 2</li>
                    <li>친구 3</li>
                </ul>
            </div>
            <div class="footer-buttons">
                <button>친구목록</button>
                <button>채팅방목록</button>
                <button>설정</button>
            </div>
        </div>
    </sec:authorize>
    <sec:authorize access="hasRole('admin')">
        <div class="admin-section">
            관리자 페이지
        </div>
    </sec:authorize>
</div>
</body>
</html>
