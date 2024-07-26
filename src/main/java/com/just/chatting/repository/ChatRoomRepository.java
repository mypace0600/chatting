package com.just.chatting.repository;

import com.just.chatting.entity.ChatRoom;
import com.just.chatting.entity.Friend;
import com.just.chatting.repository.custom.ChatRoomRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer>, QuerydslPredicateExecutor<ChatRoom>, ChatRoomRepositoryCustom {

}
