let common = {
    init: function () {
        $("#sideBarBtn").on("click", this.sideBarToggle);
        $("#closeSideBarBtn").on("click", this.sideBarToggle);
        $("#goBackBtn").on("click",this.goBack);
    },

    sideBarToggle: function () {
        let sideBarContainer = $("#sideBarContainer");
        sideBarContainer.toggleClass("activeView nonActiveView");
    },

    goBack: function() {
        location.href="/";
    }
}



common.init();
