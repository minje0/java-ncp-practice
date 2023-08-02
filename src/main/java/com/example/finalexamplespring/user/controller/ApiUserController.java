package com.example.finalexamplespring.user.controller;

import com.example.finalexamplespring.dto.ResponseDTO;
import com.example.finalexamplespring.jwt.JwtTokenProvider;
import com.example.finalexamplespring.ncloudChatApi.service.ChatApiService;
import com.example.finalexamplespring.user.dto.UserDTO;
import com.example.finalexamplespring.user.entity.User;
import com.example.finalexamplespring.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class ApiUserController {
    private final UserService userService;
    private final ChatApiService chatService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestParam UserDTO userDTO) {
        ResponseDTO<UserDTO> responseDTO = new ResponseDTO<>();

        try {
            User user = userDTO.DTOToEntity();

            user.setPassword(
                    passwordEncoder.encode(user.getPassword())
            );
            user.setRole("ROLE_USER");

            User joinUser = userService.join(user);
            chatService.join(user);
            joinUser.setPassword("");

            UserDTO joinUserDTO = joinUser.EntityToDTO();

            responseDTO.setItem(joinUserDTO);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam UserDTO userDTO) {
        ResponseDTO<UserDTO> responseDTO = new ResponseDTO<>();

        try {
            User user = userService.login(userDTO.getEmail(), userDTO.getPassword());

            if (user != null) {
                String token = jwtTokenProvider.create(user);
                user.setPassword("");

                UserDTO loginUserDTO = user.EntityToDTO();
                loginUserDTO.setToken(token);

                responseDTO.setItem(loginUserDTO);
                responseDTO.setStatusCode(HttpStatus.OK.value());

                return ResponseEntity.ok().body(responseDTO);
            } else {
                responseDTO.setErrorMessage("login failed");
                return ResponseEntity.badRequest().body(responseDTO);
            }
        } catch (Exception e) {
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
