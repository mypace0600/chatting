<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@include file="../common/head.jsp"%>
    <link rel="stylesheet" type="text/css" href="/css/user.css">
    <script src="/js/user.js"></script>
</head>
<body>
    <div class="container">
        <div class="login-box">
            <h2>로그인 화면입니다.</h2>
            <form action="/auth/loginProc" method="post">
                <div class="form-group">
                    <label for="email">이메일 주소</label>
                    <input type="email" name="email" id="email" class="form-control" placeholder="이메일을 입력해주세요"/>
                </div>
                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password" name="password" id="password" class="form-control" placeholder="비밀번호를 입력해주세요"/>
                </div>
                <button type="button" class="btn btn-primary" onclick="window.location.href='/auth/join'">회원가입</button>
                <button type="submit" class="btn btn-primary">로그인</button>
            </form>
        </div>
    </div>
</body>
</html>
