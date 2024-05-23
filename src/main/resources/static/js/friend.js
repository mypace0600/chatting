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
                alert(resp.data);
            }
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
}

friendIndex.init();