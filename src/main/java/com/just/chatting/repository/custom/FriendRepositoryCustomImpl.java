package com.just.chatting.repository.custom;

import com.just.chatting.entity.Friend;
import com.just.chatting.entity.QFriend;
import com.just.chatting.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

public class FriendRepositoryCustomImpl implements FriendRepositoryCustom{

    private JPAQueryFactory queryFactory;
    public FriendRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Friend> findAllFriendsOfMine(User user){
        return queryFactory
                .select(QFriend.friend)
                .from(QFriend.friend)
                .innerJoin(QFriend.friend.fromUser).fetchJoin()
                .where(QFriend.friend.areWeFriend.eq(true))
                .fetch();
    }
}
