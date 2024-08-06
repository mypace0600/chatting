package com.just.chatting.repository.custom;

import com.just.chatting.entity.Friend;
import com.just.chatting.entity.QFriend;
import com.just.chatting.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class FriendRepositoryCustomImpl implements FriendRepositoryCustom{

    private JPAQueryFactory queryFactory;
    public FriendRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<Friend> findAllFriendsOfMine(User user){
        return queryFactory
                .select(QFriend.friend)
                .from(QFriend.friend)
                .where(QFriend.friend.areWeFriend.eq(true)
                        .and((QFriend.friend.fromUser.id.eq(user.getId()))
                                .or(QFriend.friend.toUser.id.eq(user.getId()))))
                .fetch();
    }

    public List<Friend> findAllByToUser(User user){
        return queryFactory
                .select(QFriend.friend)
                .from(QFriend.friend)
                .innerJoin(QFriend.friend.toUser).fetchJoin()
                .innerJoin(QFriend.friend.fromUser).fetchJoin()
                .where(QFriend.friend.toUser.eq(user)
                        .and(QFriend.friend.areWeFriend.isFalse()))
                .orderBy(QFriend.friend.id.desc())
                .fetch();
    }

    public List<Friend> findAllByFromUser(User user){
        return queryFactory
                .select(QFriend.friend)
                .from(QFriend.friend)
                .innerJoin(QFriend.friend.toUser).fetchJoin()
                .innerJoin(QFriend.friend.fromUser).fetchJoin()
                .where(QFriend.friend.fromUser.eq(user)
                        .and(QFriend.friend.areWeFriend.isFalse()))
                .orderBy(QFriend.friend.id.desc())
                .fetch();
    }


    public Optional<Friend> findByToUserAndFromUser(User toUser, User fromUser) {

        Friend friend = queryFactory
                .select(QFriend.friend)
                .from(QFriend.friend)
                .where((QFriend.friend.toUser.id.eq(toUser.getId())
                        .and(QFriend.friend.fromUser.id.eq(fromUser.getId())))
                        .or(QFriend.friend.toUser.id.eq(fromUser.getId())
                                .and(QFriend.friend.fromUser.id.eq(toUser.getId()))))
                .fetchOne();

        return Optional.ofNullable(friend);
    }
}
