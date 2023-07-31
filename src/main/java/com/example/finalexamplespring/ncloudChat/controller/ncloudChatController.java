package com.example.finalexamplespring.ncloudChat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ncloudChat")
public class ncloudChatController {

    @GetMapping("/chat")
    public String getChat() {
        return "ncloudChat/ncloudChat";
    }

    @GetMapping("/chatApi")
    public String getChatApi() {
        return "ncloudChatApi/chat";
    }
}
