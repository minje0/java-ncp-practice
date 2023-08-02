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

        return userRepository.save(user);
    }

    public  UserDTO loadUserByEmail(String userEmail) {
        Optional<User> userOptional = userRepository.findByEmail(userEmail);

        if (userOptional.isEmpty()) {
            return null;
        }

        return userOptional.get().EntityToDTO();

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
