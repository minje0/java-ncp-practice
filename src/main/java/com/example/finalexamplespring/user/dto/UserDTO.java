package com.example.finalexamplespring.user.dto;

import com.example.finalexamplespring.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private int id;
    private String name;
    private String email;   //ncloudchat Id값으로 사용
    private String password;
    private String tel;
    private String birth;
    private String school;
    private String address;
    private String role;
    private String token;
    private int joinId;

    public User DTOToEntity() {
        User user = User.builder()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .password(this.password)
                .tel(this.tel)
                .birth(this.birth)
                .school(this.school)
                .address(this.address)
                .role(this.role)
                .token(this.token)
                .joinId(this.joinId)
                .build();
        return user;
    }
}
