let friendIndex = {

    init : function () {
        $("#searchFriendBtn").on("click",()=>{
            this.searchFriend();
        })
    },

    searchFriend : function () {
        let searchValue = $("#searchValue").val();
        console.log(searchValue);

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
                // 모달 엘리먼트를 가져옵니다.
                var modal = document.getElementById("myModal");
                // 모달의 정보를 표시할 엘리먼트를 가져옵니다.
                var modalInfo = document.getElementById("searchedUserInfo");
                // 검색된 사용자 정보를 모달에 표시합니다.
                modalInfo.textContent = "닉네임: " + resp.searchedUser.nickName + ", 이메일: " + resp.searchedUser.email;
                // 모달을 띄웁니다.
                modal.style.display = "block";

                // 모달의 닫기 버튼을 클릭하면 모달을 닫습니다.
                var span = document.getElementsByClassName("close")[0];
                span.onclick = function() {
                    modal.style.display = "none";
                }
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}

friendIndex.init();