package com.example.finalexamplespring.user.service;

import com.example.finalexamplespring.user.dto.UserDTO;
import com.example.finalexamplespring.user.entity.User;
import com.example.finalexamplespring.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void join(UserDTO userDTO) {
        User user = User.builder()
                .name(userDTO.getName())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .build();

        userRepository.save(user);
    }
}
