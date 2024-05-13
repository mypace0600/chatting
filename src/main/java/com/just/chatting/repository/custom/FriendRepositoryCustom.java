package com.just.chatting.repository.custom;

import com.just.chatting.entity.Friend;
import com.just.chatting.entity.User;

import java.util.List;

public interface FriendRepositoryCustom {
    List<Friend> findAllFriendsOfMine(User user);
}
