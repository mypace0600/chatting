package com.just.chatting.repository.custom;

import com.just.chatting.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
public class ChatRoomRepositoryCustomImpl implements ChatRoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public ChatRoomRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<ChatRoom> findByUsers(List<Integer> userIds, Integer fromUserId) {
        QChatRoomUser chatRoomUser = QChatRoomUser.chatRoomUser;
        QChatRoom chatRoom = QChatRoom.chatRoom;
        userIds.add(fromUserId);

        // 사용자들이 모두 포함된 채팅방을 찾는 쿼리
        List<Integer> chatRoomIds = queryFactory.select(chatRoomUser.chatRoom.id)
                .from(chatRoomUser)
                .where(chatRoomUser.user.id.in(userIds))
                .groupBy(chatRoomUser.chatRoom.id)
                .having(chatRoomUser.chatRoom.id.count().eq((long) userIds.size()))
                .fetch();

        if (chatRoomIds.isEmpty()) {
            return Optional.empty();
        }

        ChatRoom foundChatRoom = queryFactory.selectFrom(chatRoom)
                .where(chatRoom.id.in(chatRoomIds))
                .fetchFirst();

        return Optional.ofNullable(foundChatRoom);
    }

    @Override
    public List<ChatRoom> findAllByUserId(Integer userId){
        QChatRoomUser chatRoomUser = QChatRoomUser.chatRoomUser;
        QChatRoom chatRoom = QChatRoom.chatRoom;

        List<ChatRoom> chatRoomList = queryFactory.select(chatRoom)
                .from(chatRoomUser)
                .where(chatRoomUser.user.id.eq(userId))
                .fetch();

        return chatRoomList;
    }
}