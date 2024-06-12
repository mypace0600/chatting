package com.just.chatting.service;

import com.just.chatting.entity.ChatMessage;
import com.just.chatting.entity.ChatRoom;
import com.just.chatting.entity.ChatRoomUser;
import com.just.chatting.entity.User;
import com.just.chatting.repository.ChatMessageRepository;
import com.just.chatting.repository.ChatRoomRepository;
import com.just.chatting.repository.ChatRoomUserRepository;
import com.just.chatting.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ChatMessageRepository chatMessageRepository;

    public Optional<ChatRoom> findChatRoom(Integer toUserId, Integer fromUserId) {
        return chatRoomRepository.findByUsers(toUserId,fromUserId);
    }

    public ChatRoom createChatRoom(Integer toUserId,Integer fromUserId) {
        User fromUser = userRepository.findById(fromUserId).orElseThrow(EntityNotFoundException::new);
        User toUser = userRepository.findById(toUserId).orElseThrow(EntityNotFoundException::new);

        ChatRoom chatRoom = new ChatRoom();
        chatRoom = chatRoomRepository.save(chatRoom);

        ChatRoomUser chatRoomUserFrom = new ChatRoomUser();
        chatRoomUserFrom.setChatRoom(chatRoom);
        chatRoomUserFrom.setUser(fromUser);
        log.info("@@@@@@@@@ chatRoomUserFrom :{}",chatRoomUserFrom.toString());
        chatRoomUserRepository.save(chatRoomUserFrom);

        ChatRoomUser chatRoomUserTo = new ChatRoomUser();
        chatRoomUserFrom.setChatRoom(chatRoom);
        chatRoomUserFrom.setUser(toUser);
        log.info("@@@@@@@@@ chatRoomUserTo :{}",chatRoomUserTo.toString());
        chatRoomUserRepository.save(chatRoomUserTo);

        return chatRoom;
    }

    public Optional<ChatRoom> findChatRoomById(Integer chatRoomId) {
        return chatRoomRepository.findById(chatRoomId);
    }

    public ChatMessage saveMessage(int chatRoomId, ChatMessage message) {
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(chatRoomId);
        if (chatRoomOptional.isPresent()) {
            ChatRoom chatRoom = chatRoomOptional.get();
            message.setChatRoom(chatRoom);
            return chatMessageRepository.save(message);
        } else {
            throw new IllegalArgumentException("Chat room not found with ID: " + chatRoomId);
        }
    }
}
