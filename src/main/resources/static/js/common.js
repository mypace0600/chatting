let common = {
    init: function () {
        $("#sideBarBtn").on("click", this.sideBarToggle);
    },

    sideBarToggle: function () {
        let sideBarContainer = $("#sideBarContainer");
        sideBarContainer.toggleClass("activeView nonActiveView");
    }
}

common.init();
