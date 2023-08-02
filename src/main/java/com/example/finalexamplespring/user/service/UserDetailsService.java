package com.example.finalexamplespring.user.service;

import com.example.finalexamplespring.user.entity.CustomUserDetails;
import com.example.finalexamplespring.user.entity.User;
import com.example.finalexamplespring.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsService {
    private final UserRepository userRepository;

    public UserDetails loadUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return null;
        }

        return CustomUserDetails.builder()
                .user(userOptional.get())
                .build();
    }

}
