let common = {
    init: function () {
        $("#sideBarLeftBtn").on("click", this.sideBarLeftToggle);
        $("#closeSideBarBtnLeft").on("click", this.sideBarLeftToggle);
        $("#goBackBtn").on("click", this.goBack);
        $("#sideBarRightBtn").on("click", this.sideBarRightToggle);
        $("#closeSideBarBtnRight").on("click", this.sideBarRightToggle);
        $("#makeChatRoomBtn").on("click", this.openFriendListPopup);
        $("#createChatRoom").on("click",this.createChatRoom);
        $("#friendListBtn").on("click",this.friendListOpen);
        $("#chatRoomListBtn").on("click",this.chatRoomListOpen);
        // 팝업 오버레이 클릭 시 이벤트 막기
        $("#popupOverlay").on('click', function(event) {
            if (event.target === this) {
                event.stopPropagation();
            }
        });
        $(".deleteFriendBtn").on('click',function(event){
            common.deleteFriend(event.target.id);
        });
        $(".deleteChatRoomBtn").on('click',function(event){
            common.deleteChatRoomBtn(event.target.id);
        });
        $(".editBtn").on('click',this.chatRoomNameEditPopup);
        $('.closeFriendListPopup').on('click', this.closeFriendListPopup);
        $('.closeChatRoomNameEditPopup').on('click', this.closeChatRoomNameEditPopup);
        $('.chatRoomNameSaveBtn').on('click',this.chatRoomNameSave);
        $('.inviteBtn').on('click', this.openFriendListToInvitePopup);
        $('#inviteFriendBtn').on('click', this.inviteFriend);
    },

    sideBarLeftToggle: function () {
        let sideBarLeftContainer = $("#sideBarLeftContainer");
        sideBarLeftContainer.toggleClass("activeView nonActiveView");
    },

    goBack: function() {
        location.href="/";
    },

    sideBarRightToggle : function() {
        let sideBarRightContainer = $("#sideBarRightContainer");
        sideBarRightContainer.toggleClass("activeView nonActiveView");
    },

    openFriendListPopup: function() {
        document.getElementById('friendListPopup').classList.remove("noneActive");
    },

    closeFriendListPopup: function() {
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
    },

    friendListOpen : function (){
        location.href='/';
    },

    deleteFriend : function (id){
        console.log(id);
        let data = {
            toUserId : id
        }
        $.ajax({
            type: 'POST',
            url: "/friend/delete",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            if(resp.success==true){
                location.reload();
            }
        }).fail(function (error) {
            alert(error.message)
        });
    },

    deleteChatRoomBtn : function(id){
        console.log(id);
        let data = {
            chatRoomId : id
        }
        $.ajax({
            type: 'POST',
            url: "/chat/room/leave",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            if(resp.success==true){
                location.href="/";
            }
        }).fail(function (error) {
            alert(error.message)
        });
    },

    chatRoomNameEditPopup : function() {
        let chatRoomNameEditPopup = document.getElementById("chatRoomNameEditPopup");
        chatRoomNameEditPopup.classList.remove("nonActiveView");
        chatRoomNameEditPopup.classList.add("activeView");
    }
    ,
    closeChatRoomNameEditPopup : function (){
        document.getElementById('chatRoomNameEditPopup').classList.add("noneActive");
        document.getElementById('chatRoomNameEditPopup').classList.remove("activeView");
    },
    chatRoomNameSave : function (){
        let chatRoomId = document.getElementById('chatRoomId').value;
        let chatRoomName = document.getElementById('chatRoomName').value;
        let data = {
            chatRoomId: chatRoomId,
            chatRoomName: chatRoomName
        }
        $.ajax({
            type: 'POST',
            url: "/chat/room/edit",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            if(resp.success==true){
                common.closeChatRoomNameEditPopup();
            }
        }).fail(function (error) {
            alert(error.message)
        });
    },

    openFriendListToInvitePopup : function (){
        document.getElementById('friendListPopup').classList.remove("noneActive");
    },

    inviteFriend : function (){
        let chatRoomId = document.getElementById('chatRoomId').value;
        let targetUserIdList = document.querySelectorAll(".targetUserId:checked");
        let checkedUserIdList = Array.from(targetUserIdList).map(checkbox => checkbox.id);
        let data = {
            chatRoomId: chatRoomId,
            inviteFriendIdList: checkedUserIdList
        }
        $.ajax({
            type: 'POST',
            url: "/chat/room/invite",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            if(resp.success==true){
                location.reload();
            }
        }).fail(function (error) {
            alert(error.message)
        });
    }

}

common.init();
