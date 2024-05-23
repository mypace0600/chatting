<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
</head>
<body>
<header>
    <sec:authorize access="isAnonymous()">
            <div class="auth-buttons">
                <button><a href="/auth/loginForm">로그인</a></button>
                <button><a href="/auth/joinForm">회원가입</a></button>
            </div>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <div class="main-content d-flex justify-content-between align-items-center">
                <button class="btn"><i class="bi bi-list"></i></button>
                <button class="btn"><i class="bi bi-person-circle"></i></button>
            </div>
            <div class="search-bar mt-2">
                <input type="text" placeholder="닉네임 검색" id="searchValue">
                <button type="button" id="searchFriendBtn">검색</button>
            </div>
        </sec:authorize>
</header>
