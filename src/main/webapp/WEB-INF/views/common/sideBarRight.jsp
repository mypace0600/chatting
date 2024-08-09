<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div id="sideBarRightContainer" class="nonActiveView">
    <div id="sideBarBoxRight">
        <div id="sideBar">
            <div>
                <div><button type="button" class="editBtn">채팅방 이름 수정</button></div>
                <div><button type="button" class="inviteBtn" id="${chatRoom.id}">친구 초대하기</button></div>
                <div>
                    <ul>
                        <c:forEach var="chatRoomUser" items="${chatRoomUserList}">
                            <li>${chatRoomUser.user.nickName}</li>
                        </c:forEach>
                    </ul>
                </div>
                <div><button type="button" class="deleteChatRoomBtn" id="${chatRoom.id}">채팅방 퇴장하기</button></div>
            </div>
            <button type="button" id="closeSideBarBtnRight">닫기</button>
        </div>
    </div>
</div>
