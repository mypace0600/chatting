let chatIndex = {
    stompClient: null, // stompClient를 객체의 프로퍼티로 선언

    init : function (){
        $(document).ready(() => {
            this.connectToWebSocket();
        });

        // 메시지 전송 버튼 클릭 이벤트
        $("#sendButton").on("click", () => {
            let message = $("#messageInput").val();
            if (message && this.stompClient && this.stompClient.connected) { // stompClient 연결 상태 확인
                this.stompClient.send("/app/chat", {}, JSON.stringify({ message: message }));
                $("#messageInput").val(""); // 메시지 입력창 비우기
            } else {
                console.log("WebSocket is not connected.");
            }
        });
    },

    connectToWebSocket : function() {
        if (!this.stompClient || !this.stompClient.connected) {
            let socket = new SockJS('/ws'); // 올바른 엔드포인트로 변경
            this.stompClient = Stomp.over(socket);
            this.stompClient.connect({}, function(frame) {
                console.log('Connected to WebSocket server');
                // 채팅방 입장 메시지 전송
                chatIndex.stompClient.send("/app/chat", {}, JSON.stringify({ username: 'USERNAME', message: '입장했습니다.' }));

                // 메시지 수신 구독
                chatIndex.stompClient.subscribe('/room/'+roomId, function(message) {
                    let data = JSON.parse(message.body);
                    let username = data.username;
                    let messageContent = data.message;

                    // 메시지 출력 (나의 메시지 vs 다른 사용자 메시지)
                    if (username === 'USERNAME') {
                        chatIndex.appendMyMessage(messageContent);
                    } else {
                        chatIndex.appendOtherMessage(username, messageContent);
                    }
                });
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

chatIndex.init();
