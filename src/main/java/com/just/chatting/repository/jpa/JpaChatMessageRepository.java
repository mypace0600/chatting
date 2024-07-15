package com.just.chatting.repository.jpa;

import com.just.chatting.entity.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatMessageRepository extends JpaRepository<ChatMessage, Integer> {
    Page<ChatMessage> findByChatRoomId(Integer chatRoomId, Pageable pageable);
}
