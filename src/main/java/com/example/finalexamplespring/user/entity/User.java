package com.example.finalexamplespring.user.entity;

import com.example.finalexamplespring.user.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "T_USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_NO")
    private Integer id;

    @Column(name = "USER_NAME")
    private String name;

    //ncloudchat Id값으로 사용
    @Column(name = "USER_EMAIL")
    private String email;

    @Column(name = "USER_PWD")
    private String password;

    @Column(name = "USER_TEL")
    private String tel;

    @Column(name = "USER_BIRTH")
    private String birth;

    @Column(name = "USER_SCHOOL")
    private String school;

    @Column(name = "USER_ADDRESS")
    private String address;

    @Column(name = "USER_ROLE")
    private String role;

    @Column(name = "USER_TOKEN")
    private String token;

    @Column(name = "USER_JOIN_ID")
    private int joinId;

    public UserDTO EntityToDTO() {
        UserDTO userDTO = UserDTO.builder()
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
        return userDTO;
    }
}
