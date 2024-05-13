<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>로그인 화면</title>
</head>
<body>
    <div>로그인 화면입니다.</div>
    <div class="join">
        <form action="/auth/loginProc" method="post">
            <div class="form-group">
                <label for="email">이메일 주소</label>
                <input type="email" name="email" id="email" class="form-control" placeholder="이메일을 입력해주세요"/>
            </div>
            <div class="form-group">
                <label for="password">비밀번호</label>
                <input type="password" name="password" id="password" class="form-control" placeholder="비밀번호를 입력해주세요"/>
            </div>
            <button type="submit" class="btn btn-primary">로그인</button>
        </form>
    </div>
</body>
</html>

