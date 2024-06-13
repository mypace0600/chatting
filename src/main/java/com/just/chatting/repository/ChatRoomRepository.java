package com.just.chatting.repository;

import com.just.chatting.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

    @Query("SELECT cr FROM ChatRoom cr JOIN cr.chatRoomUserList cru JOIN cr.chatRoomUserList cru2 WHERE cru.user.id = ?1 And cru2.user.id = ?2")
    Optional<ChatRoom> findByUsers(Integer toUserId, Integer fromUserId);
}
