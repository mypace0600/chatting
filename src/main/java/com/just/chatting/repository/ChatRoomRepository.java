package com.just.chatting.repository;

import com.just.chatting.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

    @Query("SELECT cr FROM ChatRoom  cr JOIN cr.chatRoomUserList cru1 JOIN cr.chatRoomUserList cru2 WHERE cru1.user.id = : user1Id And cru2.user.id = : user2Id")
    Optional<ChatRoom> findByUsers(@Param("user1Id") Integer toUserId, @Param("user2Id") Integer fromUserId);
}
