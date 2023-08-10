package com.example.finalexamplespring.user.controller;

import com.example.finalexamplespring.dto.ResponseDTO;
import com.example.finalexamplespring.jwt.JwtTokenProvider;
import com.example.finalexamplespring.ncloudChatApi.service.ChatApiService;
import com.example.finalexamplespring.user.dto.JoinDTO;
import com.example.finalexamplespring.user.dto.UserDTO;
import com.example.finalexamplespring.user.entity.User;
import com.example.finalexamplespring.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class ApiUserController {
    private final UserService userService;
    private final ChatApiService chatService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinDTO joinDTO) {
        System.out.println("hello");
        System.out.println(joinDTO);
        ResponseDTO<JoinDTO> responseDTO = new ResponseDTO<>();

        UserDTO studentDTO = joinDTO.getStudentDTO();
        UserDTO parentDTO = joinDTO.getParentDTO();

        try {
            User student = studentDTO.DTOToEntity();
            User parent = parentDTO.DTOToEntity();

            String pwd = student.getBirth().toLowerCase();

            student.setPassword(passwordEncoder.encode(pwd));
            parent.setPassword(passwordEncoder.encode(pwd));

            student.setRole("ROLE_USER");
            parent.setRole("ROLE_USER");

            User studentUser = userService.join(student);

            parent.setJoinId(studentUser.getId());
            User parentUser = userService.join(parent);

            chatService.join(studentUser);
            chatService.join(parentUser);

            studentUser.setPassword("");
            parentUser.setPassword("");

            JoinDTO joinUserDTO = JoinDTO.builder()
                    .studentDTO(studentUser.EntityToDTO())
                    .parentDTO(parentUser.EntityToDTO())
                    .build();

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
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        ResponseDTO<UserDTO> responseDTO = new ResponseDTO<>();
        System.out.println(userDTO);

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
