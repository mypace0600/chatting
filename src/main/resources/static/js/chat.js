let chatIndex = {
    stompClient: null,
    currentPage: 0,
    loading: false,

    init: function() {
        $(document).ready(() => {
            this.connectToWebSocket();
            this.currentPage = 0;
            this.loadMoreMessages(); // 초기 메시지 로드
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

        // 스크롤 이벤트 리스너 추가
        $("#messageListBox").on("scroll", () => {
            if ($("#messageListBox").scrollTop() === 0 && !this.loading) {
                this.loadMoreMessages();
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
                    if (data.senderId === null) {
                        chatIndex.appendSystemMessage(data.message, data.sendDt);
                    } else {
                        let username = data.sender.id;
                        let messageContent = data.content;
                        let sendDt = new Date(data.sendDt).toLocaleString();
                        if (username == document.getElementById("senderId").value) {
                            chatIndex.appendMyMessage(messageContent, sendDt);
                        } else {
                            chatIndex.appendOtherMessage(username, messageContent, sendDt);
                        }
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

    appendSystemMessage: function(message, sendDt) {
        let messageList = $("#messageList");
        let messageItem = $('<div class="message-container system-message-container" style="display: flex; flex-direction: column; justify-content: center; align-items: center;">')
            .append($('<span class="message system-message">').text(message))
            .append($('<span class="message-date">').text(sendDt));
        messageList.append(messageItem);
        this.scrollToBottom();
    },

    scrollToBottom: function() {
        let messageListBox = $("#messageListBox");
        messageListBox.scrollTop(messageListBox[0].scrollHeight);
    },

    loadMoreMessages: function() {
        this.loading = true;

        $.ajax({
            url: `/chat/room/${roomId}/messages?page=${this.currentPage}`,
            method: 'GET',
            success: (data) => {
                console.log(data);
                let messageList = $("#messageList");
                let scrollHeightBefore = messageList[0].scrollHeight;

                data.forEach(message => {
                    let messageItem;
                    let username = message.sender.id;
                    let messageContent = message.content;
                    let sendDt = new Date(message.sendDt).toLocaleString();

                    if (username == document.getElementById("senderId").value) {
                        messageItem = $('<div class="message-container my-message-container" style="display: flex; flex-direction: column; justify-content: center; align-items: end;">')
                            .append($('<span class="message my-message">').text(messageContent))
                            .append($('<span class="message-date">').text(sendDt));
                    } else {
                        messageItem = $('<div class="message-container other-message-container" style="display: flex; flex-direction: column; justify-content: center; align-items: start;">')
                            .append($('<span class="message other-message">').html('<strong>' + username + '</strong>: ' + messageContent))
                            .append($('<span class="message-date">').text(sendDt));
                    }

                    messageList.append(messageItem);
                });

                let scrollHeightAfter = messageList[0].scrollHeight;
                $("#messageListBox").scrollTop(scrollHeightAfter - scrollHeightBefore);
                this.loading = false;
            },
            error: (error) => {
                console.log('Error loading messages:', error);
                this.loading = false;
            }
        });

        this.currentPage++;
    }
}

let roomId = document.getElementById("chatRoomId").value;
chatIndex.init();
