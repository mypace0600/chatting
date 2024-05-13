<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>메인 화면</title>
</head>
<body>
    <sec:authorize access="isAnonymous()">
        <button><a href="/auth/loginForm">로그인</a></button>
        <button><a href="/auth/joinForm">회원가입</a></button>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <button><a href="/auth/logout">로그아웃</a></button>
    </sec:authorize>
    <sec:authorize access="hasRole('admin')">
        관리자 페이지
    </sec:authorize>
</body>
</html>

