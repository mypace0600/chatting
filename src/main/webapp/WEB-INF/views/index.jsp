<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@include file="common/head.jsp"%>
<%@include file="common/header.jsp"%>
<div class="main-content">
    <div class="friends-list">
        <ul>
            <c:forEach var="friend" items="${friendList}">
                <li>${friend}</li>
            </c:forEach>
        </ul>
    </div>
</div>
<%@include file="modal/searchResult.jsp"%>
<%@include file="common/footer.jsp"%>
