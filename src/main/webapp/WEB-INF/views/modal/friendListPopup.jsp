<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div id="friendListPopup" class="noneActive">
    <div id="popupOverlay" class="overlay">
        <div class="popup">
            <div class="popup-header">
                <span>팝업 제목</span>
                <button class="close-btn" onclick="closePopup()">X</button>
            </div>
            <div class="popup-content">
                <p>팝업 내용이 여기에 표시됩니다.</p>
            </div>
        </div>
    </div>
</div>