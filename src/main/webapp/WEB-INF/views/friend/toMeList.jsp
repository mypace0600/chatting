<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@include file="../common/head.jsp"%>
<%@include file="../common/goBackHeader.jsp"%>
<%@include file="../common/sidebar.jsp"%>
<div class="main-content">
    <div class="friends-list">
        <ul>
            <c:forEach var="friend" items="${friendList}">
                <li>
                    <input type="hidden" class="fromUserId", value="${friend.fromUser.id}">
                    <span>${friend.fromUser.nickName}</span>
                    <button type="button" class="okBtn">OK</button>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
<%@include file="../modal/searchResult.jsp"%>
<%@include file="../common/footer.jsp"%>
