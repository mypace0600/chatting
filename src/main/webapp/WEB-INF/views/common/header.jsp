<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>메인</title>
    <link rel="stylesheet" type="text/css" href="/css/common.css">
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
            <div class="search-bar">
                <input type="text" placeholder="친구 검색">
                <button>검색</button>
            </div>
        </div>
    </sec:authorize>
</header>
