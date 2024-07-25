package com.just.chatting.repository;

import com.just.chatting.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    @Query("SELECT cr FROM ChatRoom cr JOIN cr.chatRoomUserList cru JOIN cr.chatRoomUserList cru2 " +
            "WHERE cru.user.id IN ?1 AND cru2.user.id = ?3 " +
            "GROUP BY cr.id " +
            "HAVING COUNT(DISTINCT cru.user.id) = ?2 + 1") // +1은 fromUserId를 포함하기 위함
    Optional<ChatRoom> findByUsers(List<Integer> toUserId, Integer size, Integer fromUserId);

}
