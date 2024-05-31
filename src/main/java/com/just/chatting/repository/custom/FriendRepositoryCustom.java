package com.just.chatting.repository.custom;

import com.just.chatting.entity.Friend;
import com.just.chatting.entity.User;

import java.util.List;
import java.util.Optional;

public interface FriendRepositoryCustom {
    List<Friend> findAllFriendsOfMine(User user);

    List<Friend> findAllByToUser(User user);

    Optional<Friend> findByToUserAndFromUser(Integer id, int fromUserId);
}
