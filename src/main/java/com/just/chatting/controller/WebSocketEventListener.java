package com.just.chatting.controller;

import com.just.chatting.entity.ChatMessage;
import com.just.chatting.entity.User;
import com.just.chatting.entity.enums.MessageType;
import com.just.chatting.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final UserService userService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event){
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionConnectedEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        Integer userId = (Integer)headerAccessor.getSessionAttributes().get("userId");

        if(null!=userId){
            log.info("user disconnected :{}",userId);
            User sender = userService.findById(userId).orElseThrow(EntityNotFoundException::new);
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setMessageType(MessageType.LEAVE.getTitle());
            chatMessage.setSender(sender);

            messagingTemplate.convertAndSend("/topic/public",chatMessage);
        }
    }
}
