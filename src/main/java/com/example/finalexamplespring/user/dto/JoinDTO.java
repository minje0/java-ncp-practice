package com.example.finalexamplespring.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JoinDTO {
    private UserDTO studentDTO;
    private UserDTO parentDTO;
}
