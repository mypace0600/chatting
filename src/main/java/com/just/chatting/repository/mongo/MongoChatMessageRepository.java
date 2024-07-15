package com.just.chatting.repository.mongo;

import com.just.chatting.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoChatMessageRepository extends MongoRepository<ChatMessage, Integer> {

}
