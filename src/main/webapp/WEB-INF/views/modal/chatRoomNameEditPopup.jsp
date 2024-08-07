<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div id="chatRoomNameEditPopup" class="noneActive">
    <div id="popupOverlay" class="overlay">
        <div class="popup">
            <div class="popup-header">
                <span>채팅방 이름 변경</span>
                <button class="close-btn closeChatRoomNameEditPopup" >X</button>
            </div>
            <div class="popup-content">
                <input type="text" id="chatRoomName" placeholder="${chatRoom.name}"/>
                <button type="button" class="chatRoomNameSaveBtn">저장</button>
            </div>
        </div>
    </div>
</div>