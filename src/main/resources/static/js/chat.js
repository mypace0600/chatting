let chatIndex = {
    stompClient: null,

    init: function() {
        $(document).ready(() => {
            this.connectToWebSocket();
        });

        $("#sendButton").on("click", () => {
            let senderId = document.getElementById("senderId").value;
            let chatRoomId = roomId;
            let message = $("#messageInput").val();
            if (message && this.stompClient && this.stompClient.connected) {
                let chatMessageDto = {
                    senderId: senderId,
                    chatRoomId: chatRoomId,
                    message: message
                };
                this.stompClient.send("/app/chat/message", {}, JSON.stringify(chatMessageDto));
                $("#messageInput").val("");
            } else {
                console.log("WebSocket is not connected.");
            }
        });
    },

    connectToWebSocket: function() {
        if (!this.stompClient || !this.stompClient.connected) {
            let socket = new SockJS('/ws');
            this.stompClient = Stomp.over(socket);
            this.stompClient.connect({}, function(frame) {
                console.log('Connected to WebSocket server');

                chatIndex.stompClient.subscribe('/topic/chat/room/' + roomId, function(message) {
                    let data = JSON.parse(message.body);
                    console.log(data);
                    let username = data.sender.id;
                    let messageContent = data.content;
                    let sendDt = new Date(data.sendDt).toLocaleString();

                    if (username == document.getElementById("senderId").value) {
                        chatIndex.appendMyMessage(messageContent,sendDt);
                    } else {
                        chatIndex.appendOtherMessage(username, messageContent,sendDt);
                    }
                });
            }, function(error) {
                console.log('WebSocket connection error: ' + error);
                setTimeout(() => chatIndex.connectToWebSocket(), 5000); // 재연결 시도
            });
        }
    },

   appendMyMessage: function(message, sendDt) {
        let messageList = $("#messageList");
        let messageItem = $('<div class="message-container my-message-container" style="display: flex; flex-direction: column; justify-content: center; align-items: end;">')
            .append($('<span class="message my-message">').text(message))
            .append($('<span class="message-date">').text(sendDt));
        messageList.append(messageItem);
        this.scrollToBottom();
   },

   appendOtherMessage: function(username, message, sendDt) {
        let messageList = $("#messageList");
        let messageItem = $('<div class="message-container other-message-container" style="display: flex; flex-direction: column; justify-content: center; align-items: start;">')
            .append($('<span class="message other-message">').html('<strong>' + username + '</strong>: ' + message))
            .append($('<span class="message-date">').text(sendDt));
        messageList.append(messageItem);
        this.scrollToBottom();
   },

    scrollToBottom: function() {
        let messageListBox = $("#messageListBox");
        messageListBox.scrollTop(messageListBox[0].scrollHeight);
    }
}

let roomId = document.getElementById("chatRoomId").value;
console.log(roomId);
chatIndex.init();
