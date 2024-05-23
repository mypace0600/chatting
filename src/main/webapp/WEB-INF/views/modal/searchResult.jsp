<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="myModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <p id="searchedUserNickName"></p>
        <p id="searchedUserEmail"></p>
        <input type="hidden" id="userId"/>
        <button type="button" id="askFriendBtn">친구신청</button>
    </div>
</div>
