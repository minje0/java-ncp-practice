package com.example.finalexamplespring.user.controller;

import com.example.finalexamplespring.user.dto.ResponseDTO;
import com.example.finalexamplespring.user.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
public class JoinController {
    private final JoinService joinService;

    @GetMapping("/user/sendEmail")
    public ResponseEntity<?> sendMail(@RequestParam("email") String email) {
        String authCode = joinService.mailSend(email);

        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        if (authCode != null && !authCode.equals("")) {
            responseDTO.setItem(authCode);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("인증 코드 생성 및 이메일 전송 실패");
        }
    }

    //email chk 로직 짜기
}
