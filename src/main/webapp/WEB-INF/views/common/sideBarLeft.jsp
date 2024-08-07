<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div id="sideBarLeftContainer" class="nonActiveView">
    <div id="sideBarBoxLeft">
        <div id="sideBar">
            <div>
                <div><a href="/friend/to-me-list">내가받은요청</a></div>
                <div><a href="/friend/from-me-list">내가보낸요청</a></div>
                <div><a href="/notice">공지사항</a></div>
                <div><a href="/qna">질문과 답</a></div>
            </div>
            <button type="button" id="closeSideBarBtnLeft">닫기</button>
        </div>
        <div id="otherPart"></div>
    </div>
</div>
