<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@include file="common/head.jsp"%>
<%@include file="common/header.jsp"%>
<%@include file="common/sidebar.jsp"%>
<div class="main-content">
    <div class="friends-list">
        <ul>
            <c:forEach var="chatRoom" items="${chatRoomList}">
                <li>
                    <span>${chatRoom.name}</span>
                    <button type="button" class="chatBtn" id="${chatRoom.id}">enter</button>
                    <button type="button" class="deleteChatRoomBtn" id="${chatRoom.id}">delete</button>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
<%@include file="modal/searchResult.jsp"%>
<%@include file="modal/friendListPopup.jsp"%>
<%@include file="common/footer.jsp"%>
