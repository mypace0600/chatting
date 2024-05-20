package com.just.chatting.service;


import com.just.chatting.entity.Mail;
import com.just.chatting.repository.MailRepository;
import jakarta.mail.PasswordAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    @Value("${email.admin.id}")
    private String user;
    @Value("${email.admin.password}")
    private String password;

    //의존성 주입을 통해서 필요한 객체를 가져온다.
    private final JavaMailSender emailSender;

    private final MailRepository repository;

    public String createVerifyCode(){
        String code = "";

        // 난수 5자리 생성
        Random random =new Random();
        StringBuilder randomData  = new StringBuilder();
        for(int i=0;i<5;i++){
            if(random.nextBoolean()){
                randomData.append((char) ((int) (random.nextInt(26)) + 97));
            }else{
                randomData.append(random.nextInt(10));
            }
        }

        code = String.valueOf(randomData);
        log.debug("@@@@@@@@@@@@@@ code :{}",code);
        return code;
    }

    public void save(Mail email){
        repository.save(email);
    }

    public MimeMessage createEmailForm(String email, String discountCode) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        message.setFrom(new InternetAddress(user));
        message.addRecipients(MimeMessage.RecipientType.TO,email);
        message.setSubject("확인 코드");
        message.setText(discountCode);
        return message;
    }

    public void send(Mail email) throws MessagingException {
        String verifyCode = createVerifyCode();
        email.setVerifyCode(verifyCode);
        save(email);

        MimeMessage message = createEmailForm(email.getEmailAddress(),verifyCode);

        emailSender.send(message);
    }
    private class SMTPAuthenticator extends jakarta.mail.Authenticator{
        public PasswordAuthentication getPasswordAuthentication(){
            return new PasswordAuthentication(user,password);
        }
    }

}
