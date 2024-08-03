package com.just.chatting.repository.custom;


import com.just.chatting.entity.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepositoryCustom {

    Optional<ChatRoom> findByUsers(List<Integer> toUserIdList,Integer fromUserId);
    List<ChatRoom> findAllByUserId(Integer userId);
}
