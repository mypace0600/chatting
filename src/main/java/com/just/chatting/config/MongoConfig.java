package com.just.chatting.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = "com.just.chatting.repository.mongo",
        mongoTemplateRef = "mongoTemplate"
)
public class MongoConfig {
}
