package com.just.chatting.repository;

import com.just.chatting.entity.Mail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailRepository extends JpaRepository<Mail, Integer> {
    Optional<Mail> findByVerifyCodeAndEmailAddress(String verifyCode, String emailAddress);
}
