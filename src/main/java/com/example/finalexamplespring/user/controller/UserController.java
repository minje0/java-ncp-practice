package com.example.finalexamplespring.user.controller;

import com.example.finalexamplespring.ncloudChatApi.service.ChatApiService;
import com.example.finalexamplespring.user.dto.UserDTO;
import com.example.finalexamplespring.user.entity.User;
import com.example.finalexamplespring.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final ChatApiService chatApiService;

    @GetMapping("/signup")
    public String signup() {
        return "user/sign";
    }

    @PostMapping("/signup")
    public String signup(UserDTO userDTO) throws Exception {
        User joinUser = userDTO.DTOToEntity();
        userService.join(joinUser);
        chatApiService.join(joinUser);

        return "user/main";
    }
}
