<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div id="friendListPopup" class="noneActive">
    <div id="popupOverlay" class="overlay">
        <div class="popup">
            <div class="popup-header">
                <span>초대할 친구 목록</span>
                <button class="close-btn closeFriendListPopup" onclick="closePopup()">X</button>
            </div>
            <div class="popup-content">
                <ul>
                    <c:forEach var="user" items="${friendList}">
                        <li>
                            <input type="checkbox" class="targetUserId" id="${user.id}"/>
                            <span>${user.nickName}</span>
                        </li>
                    </c:forEach>
                </ul>
                <button type="button" id="createChatRoom">채팅방생성</button>
            </div>
        </div>
    </div>
</div>