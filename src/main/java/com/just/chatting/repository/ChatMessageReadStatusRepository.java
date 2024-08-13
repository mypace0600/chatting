package com.just.chatting.repository;

import com.just.chatting.entity.ChatMessageReadStatus;
import com.just.chatting.entity.ChatRoom;
import com.just.chatting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageReadStatusRepository extends JpaRepository<ChatMessageReadStatus, Integer> {
    List<ChatMessageReadStatus> findByChatMessageChatRoomIdAndUserIdAndIsReadFalse(Integer chatRoomId, Integer userId);
    int countByChatMessageChatRoomIdAndUserIdAndIsReadFalse(Integer chatRoomId, Integer userId);

    Optional<ChatMessageReadStatus> findByChatMessageIdAndUserId(Integer messageId, Integer id);
}
