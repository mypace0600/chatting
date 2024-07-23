<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div id="friendListPopup" class="noneActive">
    <div id="popupOverlay" class="overlay">
        <div class="popup">
            <div class="popup-header">
                <span>초대할 친구 목록</span>
                <button class="close-btn" onclick="closePopup()">X</button>
            </div>
            <div class="popup-content">
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
    </div>
</div>