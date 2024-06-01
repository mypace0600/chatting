<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@include file="common/head.jsp"%>
<%@include file="common/header.jsp"%>
<%@include file="common/sidebar.jsp"%>
<div class="main-content">
    <div class="friends-list">
        <ul>
            <c:forEach var="friend" items="${friendList}">
                <li id="friend${friend.toUser.id}">
                    <input type="hidden" class="fromUserId", value="${friend.toUser.id}">
                    <span>${friend.toUser.nickName}</span>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
<%@include file="modal/searchResult.jsp"%>
<%@include file="common/footer.jsp"%>
