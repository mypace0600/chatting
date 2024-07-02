let chatIndex = {
    stompClient: null,

    init : function () {
        $(document).ready(() => {
            this.connectToWebSocket();
        });

        $("#sendButton").on("click", () => {
            let senderId = document.getElementById("sender").value;
            let chatRoomId =  roomId;
            let message = $("#messageInput").val();
            if (message && this.stompClient && this.stompClient.connected) {
                this.stompClient.send("/app/chat", {}, JSON.stringify({ message: message }));
                $("#messageInput").val("");
            } else {
                console.log("WebSocket is not connected.");
            }
        });
    },

    connectToWebSocket : function() {
        if (!this.stompClient || !this.stompClient.connected) {
            let socket = new SockJS('/ws');
            this.stompClient = Stomp.over(socket);
            this.stompClient.connect({}, function(frame) {
                console.log('Connected to WebSocket server');
                chatIndex.stompClient.send("/app/chat", {}, JSON.stringify({ username: 'USERNAME', message: '입장했습니다.' }));

                chatIndex.stompClient.subscribe('/topic/' + roomId, function(message) {
                    let data = JSON.parse(message.body);
                    let username = data.username;
                    let messageContent = data.message;

                    if (username === 'USERNAME') {
                        chatIndex.appendMyMessage(messageContent);
                    } else {
                        chatIndex.appendOtherMessage(username, messageContent);
                    }
                });
            }, function(error) {
                console.log('WebSocket connection error: ' + error);
                setTimeout(() => chatIndex.connectToWebSocket(), 5000); // 재연결 시도
            });
        }
    },

    appendMyMessage : function(message) {
        let messageList = $("#messageList");
        let messageItem = $('<li class="message my-message">').text(message);
        messageList.append(messageItem);
        this.scrollToBottom();
    },

    appendOtherMessage : function (username, message) {
        let messageList = $("#messageList");
        let messageItem = $('<li class="message other-message">').html('<strong>' + username + '</strong>: ' + message);
        messageList.append(messageItem);
        this.scrollToBottom();
    },

    scrollToBottom : function() {
        let messageListBox = $("#messageListBox");
        messageListBox.scrollTop(messageListBox[0].scrollHeight);
    },
}

let roomId = document.getElementById("chatRoomId").value; // 실제 채팅방 ID로 변경
console.log(roomId); // roomId 값이 올바르게 출력되는지 확인
chatIndex.init();
