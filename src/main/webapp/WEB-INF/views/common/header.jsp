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
        <div class="main-content">
            <button><a href="/auth/logout">로그아웃</a></button>
            <div class="search-bar">
                <input type="text" placeholder="닉네임 검색" id="searchValue">
                <button type="button" id="searchFriendBtn">검색</button>
            </div>
        </div>
    </sec:authorize>
</header>
