<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@include file="../common/head.jsp"%>
<%@include file="../common/goBackHeader.jsp"%>
<div class="main-content">
    <div id="profileContainer">
        <div id="photoBox"> </div>
        <div id="line"></div>
        <div id="infoBox">
            <input type="hidden" id="userId" value="${user.id}"/>
            <div class="infoDetail" id="nicknameBox">
                <span id="labelForNickname">nickname : </span>
                <span id="currentNickname">${user.nickName}</span>
            </div>
            <div class="infoDetail"  id="emailBox">
                <span id="labelForEmail">email : </span>
                <span>${user.email}</span>
            </div>
        </div>
    </div>
    <div id="buttonBox">
        <button type="button" onclick="location.href='/auth/edit'">edit</button>
        <button type="button" onclick="location.href='/auth/logout'">logout</button>
    </div>
</div>
<script src="/js/user.js"></script>
<script src="/js/common.js"></script>
