package com.just.chatting.controller;

import com.just.chatting.common.ResponseDto;
import com.just.chatting.entity.Mail;
import com.just.chatting.service.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    @PostMapping("/mail/send-verify-code")
    public ResponseDto<Integer> sendVerifyCode(@RequestBody Mail mail) throws MessagingException {
        mailService.send(mail);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }
}
