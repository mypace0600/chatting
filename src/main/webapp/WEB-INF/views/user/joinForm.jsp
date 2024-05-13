<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>회원가입 화면</title>
</head>
<body>
    <div>회원가입 화면입니다.</div>
    <div class="join">
        <form action="<c:url value='/auth/joinProc'/>" method="post" modelAttribute="user">
            <div class="form-group">
                <label for="email">email</label>
                <input type="email" id="email" name="email" class="form-control" placeholder="Enter Email"/>
            </div>
            <div class="form-group">
                <label for="password">password</label>
                <input type="password" id="password" name="password" class="form-control" placeholder="Enter Password"/>
            </div>
            <div style="text-align:center">
                <button type="submit" class="btn btn-primary">회원가입</button>
            <button type="button" class="btn btn-primary" onclick="history.back()">뒤로가기</button>
            </div>
        </form>
    </div>
</body>
</html>
