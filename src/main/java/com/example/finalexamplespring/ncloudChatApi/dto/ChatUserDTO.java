package com.example.finalexamplespring.ncloudChatApi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatUserDTO {
    @JsonProperty("userId")
    private String userId;

    @JsonProperty("name")
    private String name;
}
