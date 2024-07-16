package com.just.chatting.repository;

import com.just.chatting.entity.Friend;
import com.just.chatting.repository.custom.FriendRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FriendRepository extends JpaRepository<Friend, Integer>, QuerydslPredicateExecutor<Friend>, FriendRepositoryCustom {

}
