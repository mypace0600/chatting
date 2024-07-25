let friendIndex = {

    init : function () {
        $("#searchFriendBtn").on("click",()=>{
            this.searchFriend();
        });
        $("#askFriendBtn").on("click",()=>{
            this.askFriend();
        });
        $(".friends-list").on("click", ".okBtn", function() {
            let id = $(this).closest("li").find(".fromUserId").val();
            friendIndex.approveFriend(id);
        });
        $(".friends-list").on("click", ".cancelBtn", function() {
            let id = $(this).closest("li").find(".toUserId").val();
            friendIndex.cancelRequestFriend(id);
        });
        $(".friends-list").on("click", ".chatBtn", function() {
            let id = $(this).closest("li").find(".toUserId").val();
            friendIndex.chatEnterToggle(id);
        });
    },

    searchFriend : function () {
        let searchValue = $("#searchValue").val();
        let data = {
            searchValue : searchValue
        };

        $.ajax({
            type: "POST",
            url: "/friend/search",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            if (resp.status === 500) {
                alert("해당 닉네임을 가진 사용자가 없습니다.");
            } else {

                let modal = document.getElementById("myModal");
                let searchedUserNickName = document.getElementById("searchedUserNickName");
                searchedUserNickName.textContent = "닉네임: " + resp.searchedUser.nickName;
                let searchedUserEmail = document.getElementById("searchedUserEmail");
                searchedUserEmail.textContent = "이메일: " + resp.searchedUser.email;
                let userId = document.getElementById("userId");
                userId.value = resp.searchedUser.id;

                modal.style.display = "block";

                let span = document.getElementsByClassName("close")[0];
                span.onclick = function() {
                    modal.style.display = "none";
                }
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    askFriend : function () {
        let userId = $("#userId").val();
        let data = {
            toUserId : userId
        };

        $.ajax({
            type: "POST",
            url: "/friend/ask",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            if(resp.success === true) {
                alert("친구 신청 성공");
            } else {
                alert("친구 신청 실패")
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    approveFriend : function (id) {
        let userId = parseInt(id);
        let data = {
            fromUserId : userId
        };

        $.ajax({
            type: "POST",
            url: "/friend/approve",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function (resp) {
            if(resp.success === true) {
                alert("친구 승인 완료");
                location.reload();
            } else {
                alert("친구 승인 실패")
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    cancelRequestFriend:function (id){
        let userId = parseInt(id);
        let data = {
            toUserId : userId
        };

        $.ajax({
            type:'POST',
            url: "/friend/request-cancel",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType:"json"
        }).done(function (resp){
            if(resp.success === true) {
                alert("친구 요청 삭제 완료");
                location.reload();
            } else {
                alert("친구 요청 삭제 실패")
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    chatEnterToggle: function (id) {
        console.log(id);
        let userIdList = [];
        userIdList.push(id);
        console.log(userIdList);
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
            if (resp.chatRoomIsPresent === true) {
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
    }

}

friendIndex.init();