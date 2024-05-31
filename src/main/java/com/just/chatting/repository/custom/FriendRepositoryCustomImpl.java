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
                .innerJoin(QFriend.friend.fromUser).fetchJoin()
                .where(QFriend.friend.areWeFriend.eq(true))
                .fetch();
    }

    public List<Friend> findAllByToUser(User user){
        return queryFactory
                .select(QFriend.friend)
                .from(QFriend.friend)
                .innerJoin(QFriend.friend.toUser).fetchJoin()
                .innerJoin(QFriend.friend.fromUser).fetchJoin()
                .where(QFriend.friend.toUser.eq(user))
                .orderBy(QFriend.friend.id.desc())
                .fetch();
    }


    public Optional<Friend> findByToUserAndFromUser(Integer toUserId, int fromUserId) {

        Friend friend = queryFactory
                .select(QFriend.friend)
                .from(QFriend.friend)
                .where(QFriend.friend.toUser.id.eq(toUserId)
                        .and(QFriend.friend.fromUser.id.eq(fromUserId))
                        .and(QFriend.friend.areWeFriend.isFalse()))
                .fetchOne();

        return Optional.ofNullable(friend);
    }
}
