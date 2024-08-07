<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
</head>
<body>
<header>
    <div class="headerBox">
        <button class="btn" id="sideBarLeftBtn"><i class="bi bi-list"></i></button>
        <sec:authorize access="isAnonymous()">
            <button class="btn" id="profileBtn" onClick="location.href='/auth/loginForm'"><i class="bi bi-person-circle"></i></button>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <button class="btn" id="profileBtn" onClick="location.href='/user/myInfo'"><i class="bi bi-person-circle"></i></button>
        </sec:authorize>
    </div>
    <div class="search-bar mt-2 searchBox">
        <input type="text" placeholder="닉네임 검색" id="searchValue">
        <button type="button" id="searchFriendBtn">검색</button>
    </div>
</header>
