<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<footer>
    <sec:authorize access="isAuthenticated()">
        <div class="footer-buttons">
            <c:if test="${target eq 'friend'}">
                <button type="button" id="chatRoomListBtn">채팅방목록보기</button>
            </c:if>
            <c:if test="${target eq 'chatRoom'}">
                <button type="button" id="friendListBtn">친구목록보기</button>
            </c:if>
            <button type="button" id="makeChatRoomBtn">채팅방만들기</button>
        </div>
    </sec:authorize>
    <sec:authorize access="hasRole('admin')">
        <div class="admin-section">
            관리자 페이지
        </div>
    </sec:authorize>
</footer>
</body>
</html>
<script src="/js/friend.js"></script>
<script src="/js/common.js"></script>
