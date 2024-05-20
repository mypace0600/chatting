<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@include file="../common/head.jsp"%>
    <link rel="stylesheet" type="text/css" href="/css/user.css">
</head>
<body>
    <div class="container">
        <div class="join-box">
            <div>회원가입 화면입니다.</div>
            <form action="/auth/joinProc" method="post">
                <div class="form-group">
                    <label for="email">이메일 주소</label>
                    <input type="email" name="email" id="email" class="form-control" placeholder="이메일을 입력해주세요"/>
                    <button type="button" class="btn" id="emailDuplicate">이메일 중복 확인</button>
                    <input type="hidden" name="emailDuplicateCheck" id="emailDuplicateCheck" value="false"/>
                    <button type="button" class="btn" >이메일 인증번호 발송</button>
                </div>
                <div class="form-group">
                    <label for="emailVerificationCode">이메일 인증번호</label>
                    <input type="text" name="emailVerificationCode" id="emailVerificationCode" class="form-control" placeholder="인증번호를 입력해주세요"/>
                    <input type="hidden" name="emailVerifyCheck" id="emailVerifyCheck" value="false"/>
                    <button type="button" class="btn" onclick="verifyEmailCode()">인증번호 확인</button>
                </div>
                <div class="form-group">
                    <label for="password">비밀번호</label>
                    <input type="password" name="password" id="password" class="form-control" placeholder="비밀번호를 입력해주세요"/>
                </div>
                <div class="form-group">
                    <label for="confirmPassword">비밀번호 확인</label>
                    <input type="password" name="confirmPassword" id="confirmPassword" class="form-control" placeholder="비밀번호를 다시 입력해주세요"/>
                    <input type="hidden" name="confirmPasswordCheck" id="confirmPasswordCheck" value="false"/>
                </div>
                <div class="form-group">
                    <label for="nickname">닉네임</label>
                    <input type="text" name="nickname" id="nickname" class="form-control" placeholder="닉네임을 입력해주세요"/>
                    <input type="hidden" name="nicknameDuplicateCheck" id="nicknameDuplicateCheck" value="false"/>
                    <button type="button" class="btn" onclick="checkNicknameDuplicate()">닉네임 중복 확인</button>
                </div>
                <button type="submit" class="btn btn-primary">회원가입</button>
                <button type="button" class="btn btn-secondary" onclick="history.back()">뒤로가기</button>
            </form>
        </div>
    </div>
</body>
</html>
<script src="/js/user.js"></script>