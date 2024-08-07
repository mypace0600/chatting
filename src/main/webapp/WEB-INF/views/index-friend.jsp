<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@include file="common/head.jsp"%>
<%@include file="common/header.jsp"%>
<%@include file="common/sideBarLeft.jsp"%>
<div class="main-content">
    <div class="friends-list">
        <ul>
            <c:forEach var="user" items="${friendList}">
                <li>
                    <span>${user.nickName}</span>
                    <button type="button" class="friendBtn" id="${user.id}">chat</button>
                    <button type="button" class="deleteFriendBtn" id="${user.id}">delete</button>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
<%@include file="modal/searchResult.jsp"%>
<%@include file="modal/friendListPopup.jsp"%>
<%@include file="common/footer.jsp"%>
