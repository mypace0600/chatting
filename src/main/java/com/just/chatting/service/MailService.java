package com.just.chatting.service;


import com.just.chatting.entity.Mail;
import com.just.chatting.repository.MailRepository;
import lombok.RequiredArgsConstructor;
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
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;


@Service
@RequiredArgsConstructor
public class MailService {
    @Value("${EMAIL_SEND_INFO.adminEmail}")
    String user;
    @Value("${EMAIL_SEND_INFO.adminEmailPassword}")
    String password;

    //의존성 주입을 통해서 필요한 객체를 가져온다.
    private final JavaMailSender emailSender;

    private final MailRepository repository;

    private String encrypt(String code){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        md.update(code.getBytes());

        return bytesToHex(md.digest());
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    public String createVerifyCode(){
        String code = "";

        // 시간데이터 생성
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String timeData = formatter.format(LocalDateTime.now());

        // 난수 10자리 생성
        Random random =new Random();
        String randomData  = "";
        for(int i=0;i<10;i++){
            if(random.nextBoolean()){
                randomData+=((char)((int)(random.nextInt(26))+97));
            }else{
                randomData+=((random.nextInt(10)));
            }
        }

        code = timeData+randomData;

        return encrypt(code);
    }

    public boolean checkDisCountCodeDistinct(List<String> codeList){

        boolean result = true;

        for(int i = 0; i<codeList.size();i++){
            for(int j = 0; j<i; j++){
                if(codeList.get(i).equals(codeList.get(j))){
                    return !result;
                }
            }
        }

        return result;
    }

    public void save(Mail email){
        repository.save(email);
    }

    public MimeMessage createEmailForm(String email, String discountCode) throws MessagingException {
        String setFrom = user;
        String toEmail = email;
        String title = "할인코드";

        MimeMessage message = emailSender.createMimeMessage();
        message.setFrom(new InternetAddress(user));
        message.addRecipients(MimeMessage.RecipientType.TO,email);
        message.setSubject("할인 코드");
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

}
