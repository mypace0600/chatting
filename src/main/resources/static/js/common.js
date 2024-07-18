let common = {
    init: function () {
        $("#sideBarBtn").on("click", this.sideBarToggle);
        $("#closeSideBarBtn").on("click", this.sideBarToggle);
        $("#goBackBtn").on("click", this.goBack);
        $("#makeChatRoomBtn").on("click", this.friendListPopupShow);

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
    }
}

common.init();
