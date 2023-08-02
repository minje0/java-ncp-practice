package com.example.finalexamplespring.user.service;

import com.example.finalexamplespring.user.dto.UserDTO;
import com.example.finalexamplespring.user.entity.User;
import com.example.finalexamplespring.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User join(User user) {
        if(user == null || user.getEmail() == null) {
            throw new RuntimeException("invalid argument");
        }

        if(userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("already exist id");
        }

        switch (user.getRole()) {
            case "학생": user.setRole("ROLE_STUDENT");break;
            case "학부모": user.setRole("ROLE_PARENT");break;
            case "선생": user.setRole("ROLE_TEACHER");break;
            default: user.setRole("ROLE_USER");
        }

        return userRepository.save(user);
    }

    public User login(String email, String password) {
        Optional<User> loginUser = userRepository.findByEmail(email);

        if (loginUser.isEmpty()) {
            throw new RuntimeException("id not exist");
        }

        if (!passwordEncoder.matches(password, loginUser.get().getPassword())) {
            throw new RuntimeException("wrong pw");
        }

        return loginUser.get();
    }
}
