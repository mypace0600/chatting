package com.just.chatting.controller;

import com.just.chatting.common.ResponseDto;
import com.just.chatting.entity.Mail;
import com.just.chatting.service.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    @PostMapping("/mail/send-verify-code")
    public ResponseDto<Integer> sendVerifyCode(@RequestBody Mail mail) throws MessagingException {
        mailService.send(mail);
        return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
    }

    @PostMapping("/mail/check-verify-code")
    public ResponseDto<Boolean> checkVerifyCode(@RequestBody Mail mail){
        boolean checkResult = mailService.checkVerifyCode(mail);
        return new ResponseDto<Boolean>(HttpStatus.OK.value(), checkResult);
    }
}
