package com.just.chatting.repository;

import com.just.chatting.entity.ChatRoom;
import com.just.chatting.entity.ChatRoomUser;
import com.just.chatting.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Integer> {
    Optional<ChatRoomUser> findByChatRoomAndUser(ChatRoom chatRoom, User user);

    List<ChatRoomUser> findAllByChatRoom(ChatRoom chatRoom);
}
