package com.example.finalexamplespring.ncloudChatApi.controller;

import com.example.finalexamplespring.ncloudChatApi.service.ChatApiService;
import com.example.finalexamplespring.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/ncloudChatApi")
@RequiredArgsConstructor
@RestController
public class ChatApiController {
    private final ChatApiService chatApiService;

    @GetMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody User user) throws Exception {
        return chatApiService.deleteUser(user);
    }
}
