package com.just.chatting.service;

import com.just.chatting.entity.ChatMessage;
import com.just.chatting.entity.ChatRoom;
import com.just.chatting.entity.ChatRoomUser;
import com.just.chatting.entity.User;
import com.just.chatting.repository.jpa.ChatRoomRepository;
import com.just.chatting.repository.jpa.ChatRoomUserRepository;
import com.just.chatting.repository.jpa.JpaChatMessageRepository;
import com.just.chatting.repository.jpa.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final JpaChatMessageRepository jpaChatMessageRepository;

    public Optional<ChatRoom> findChatRoomByUsers(Integer toUserId, Integer fromUserId) {
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
        chatRoomUserRepository.save(chatRoomUserFrom);

        ChatRoomUser chatRoomUserTo = new ChatRoomUser();
        chatRoomUserTo.setChatRoom(chatRoom);
        chatRoomUserTo.setUser(toUser);
        chatRoomUserRepository.save(chatRoomUserTo);

        return chatRoom;
    }

    public Optional<ChatRoom> findChatRoomById(Integer chatRoomId) {
        return chatRoomRepository.findById(chatRoomId);
    }

    public void saveMessage(ChatMessage message) {
        jpaChatMessageRepository.save(message);
    }

    public Page<ChatMessage> findChatMessagesByChatRoomId(Integer chatRoomId, Pageable pageable) {
        return jpaChatMessageRepository.findByChatRoomId(chatRoomId,pageable);
    }
}
