<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@include file="../common/head.jsp"%>
<%@include file="../common/goBackHeader.jsp"%>
<div class="main-content">
    <div class="friends-list">
        <ul>
            <c:forEach var="friend" items="${friendList}">
                <li>
                    <input type="hidden" class="toUserId", value="${friend.toUser.id}">
                    <span>${friend.toUser.nickName}</span>
                    <button type="button" class="cancelBtn">Cancel</button>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
<%@include file="../modal/searchResult.jsp"%>
<%@include file="../common/footer.jsp"%>
