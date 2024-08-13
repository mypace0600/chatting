package com.just.chatting.controller;

import com.just.chatting.dto.ChatMessageDto;
import com.just.chatting.entity.*;
import com.just.chatting.repository.ChatMessageReadStatusRepository;
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

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final UserRepository userRepository;
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatMessageReadStatusRepository chatMessageReadStatusRepository;


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

        // 각 사용자의 읽음 상태를 초기화
        List<ChatRoomUser> usersInRoom = chatRoom.getChatRoomUserList();
        for (ChatRoomUser chatRoomUser : usersInRoom) {
            User user = chatRoomUser.getUser();
            boolean isRead = user.equals(sender); // 발송자는 바로 읽은 것으로 처리
            ChatMessageReadStatus readStatus = new ChatMessageReadStatus(chatMessage, user, isRead);
            chatMessageReadStatusRepository.save(readStatus);
        }

        // 메시지를 특정 채팅방에 브로드캐스트
        messagingTemplate.convertAndSend("/topic/chat/room/" + chatMessageDto.getChatRoomId(), chatMessage);
    }
}
