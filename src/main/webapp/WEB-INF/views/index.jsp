<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@include file="common/head.jsp"%>
<%@include file="common/header.jsp"%>
<%@include file="common/sidebar.jsp"%>
<div class="main-content">
    <div class="friends-list">
        <ul>
            <c:forEach var="user" items="${friendList}">
                <li>
                    <input type="hidden" class="toUserId", value="${user.id}">
                    <span>${user.nickName}</span>
                    <button type="button" class="chatBtn">chat</button>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
<%@include file="modal/searchResult.jsp"%>
<%@include file="common/footer.jsp"%>
