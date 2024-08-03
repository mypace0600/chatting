let common = {
    init: function () {
        $("#sideBarBtn").on("click", this.sideBarToggle);
        $("#closeSideBarBtn").on("click", this.sideBarToggle);
        $("#goBackBtn").on("click", this.goBack);
        $("#makeChatRoomBtn").on("click", this.friendListPopupShow);
        $("#createChatRoom").on("click",this.createChatRoom);
        $("#chatRoomListBtn").on("click",this.chatRoomListOpen);
        // 팝업 오버레이 클릭 시 이벤트 막기
        $("#popupOverlay").on('click', function(event) {
            if (event.target === this) {
                event.stopPropagation();
            }
        });

        // 닫기 버튼 이벤트 설정
         $('.close-btn').on('click', this.closePopup);
    },

    sideBarToggle: function () {
        let sideBarContainer = $("#sideBarContainer");
        sideBarContainer.toggleClass("activeView nonActiveView");
    },

    goBack: function() {
        location.href="/";
    },

    friendListPopupShow: function() {
        document.getElementById('friendListPopup').classList.remove("noneActive");
    },

    closePopup: function() {
        document.getElementById('friendListPopup').classList.add("noneActive");
    },

    createChatRoom : function (){
        let targetUserIdList = document.querySelectorAll(".targetUserId");
        let userIdList = [];
        targetUserIdList.forEach((checkbox) => {
            if (checkbox.checked) {
                userIdList.push(checkbox.id);
            }
        });
        let data = { toUserIdList: userIdList };
        // 채팅방 확인 요청을 Promise로 처리
        function checkChatRoom() {
            return new Promise((resolve, reject) => {
                $.ajax({
                    type: 'POST',
                    url: "/chat/check",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    dataType: "json"
                }).done(function (resp) {
                     resolve(resp);
                }).fail(function (error) {
                     reject(error);
                });
            });
        }

        // 채팅방 생성 요청을 Promise로 처리
        function createChatRoom() {
            return new Promise((resolve, reject) => {
                $.ajax({
                    type: 'POST',
                    url: "/chat/room",
                    data: JSON.stringify(data),
                    contentType: "application/json; charset=utf-8",
                    dataType: "json"
                }).done(function (resp) {
                    resolve(resp);
                }).fail(function (error) {
                    reject(error);
                });
            });
        }

        // 채팅방 확인 후 처리
        checkChatRoom().then(resp => {
            if (resp.success === true) {
                let chatRoomId = resp.chatRoomId;
                location.href = "/chat/room/" + chatRoomId;
            } else {
                 // 채팅방이 없을 경우, 새로 생성
                return createChatRoom();
            }
        }).then(createResp => {
            if (createResp) { // createResp가 undefined가 아닌 경우 처리
                if (createResp.success === true) {
                    let chatRoomId = createResp.chatRoomId;
                    location.href = "/chat/room/" + chatRoomId;
                } else {
                    alert("채팅방 생성 실패");
                }
            }
        }).catch(error => {
            alert("오류 발생: " + JSON.stringify(error));
        });
    },

    chatRoomListOpen : function (){
        location.href='/chat/rooms';
    }
}

common.init();
