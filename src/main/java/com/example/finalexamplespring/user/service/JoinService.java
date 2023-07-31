package com.example.finalexamplespring.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class JoinService {
    private final JavaMailSender javaMailSender;
    public String mailSend(String email){
        //인증코드 생성
        String authCode = UUID.randomUUID().toString().substring(0, 10);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("[에듀벤처] 회원가입 인증 코드 발급 안내");
        simpleMailMessage.setText("안녕하세요. 에듀벤처 회원가입 인증 코드 안내 관련 이메일 입니다. " +
                "생성된 인증 코드: " + authCode);

        javaMailSender.send(simpleMailMessage);

        return authCode;
    }
}