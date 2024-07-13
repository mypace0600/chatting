package com.just.chatting.controller;

import com.just.chatting.common.CamelCaseMap;
import com.just.chatting.config.security.PrincipalDetail;
import com.just.chatting.dto.ChatMessageDto;
import com.just.chatting.dto.ChatMessageRequestDto;
import com.just.chatting.dto.ChatRoomDto;
import com.just.chatting.entity.ChatMessage;
import com.just.chatting.entity.ChatRoom;
import com.just.chatting.entity.User;
import com.just.chatting.repository.ChatRoomRepository;
import com.just.chatting.repository.UserRepository;
import com.just.chatting.service.ChatService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final SimpMessageSendingOperations messagingTemplate;



    @MessageMapping("/chat/message")
    @Transactional
    public void sendMessage(ChatMessageDto chatMessageDto) {

        // 메시지를 데이터베이스에 저장
        ChatMessage chatMessage = new ChatMessage();
        ChatRoom chatRoom = chatService.findChatRoomById(chatMessageDto.getChatRoomId()).orElseThrow(EntityNotFoundException::new);
        User sender = userRepository.findById(chatMessageDto.getSenderId()).orElseThrow(EntityNotFoundException::new);
        chatMessage.setChatRoom(chatRoom);
        chatMessage.setSender(sender);
        chatMessage.setContent(chatMessageDto.getMessage());

        chatService.saveMessage(chatMessage);

        // 메시지를 특정 채팅방에 브로드캐스트
        messagingTemplate.convertAndSend("/topic/chat/room/" + chatMessageDto.getChatRoomId(), chatMessage);
    }
}
