package com.just.chatting.repository.jpa;

import com.just.chatting.entity.Friend;
import com.just.chatting.repository.jpa.custom.FriendRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FriendRepository extends JpaRepository<Friend, Integer>, QuerydslPredicateExecutor<Friend>, FriendRepositoryCustom {

}
