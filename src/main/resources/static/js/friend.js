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
            } else {
                alert("친구 승인 실패")
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}

friendIndex.init();