package com.example.finalexamplespring.ncloudChatApi.service;

import com.example.finalexamplespring.ncloudChatApi.dto.ChatUserDTO;
import com.example.finalexamplespring.ncloudChatApi.dto.MemberResponseDTO;
import com.example.finalexamplespring.user.dto.UserDTO;
import com.example.finalexamplespring.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class ChatApiService {
    @Value("${cloud.ncp.ncloudchat.project.id}")
    String PROJECT_ID;
    @Value("${cloud.ncp.ncloudchat.api.key}")
    String API_KEY;
    String BASE_URL = "https://dashboard-api.ncloudchat.naverncp.com/v1/api";

    public ResponseEntity<MemberResponseDTO> joinUser(UserDTO userDTO) throws Exception {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(BASE_URL);
        urlBuilder.append("/members");

        String url = urlBuilder.toString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("x-project-id", PROJECT_ID);
        headers.set("x-api-key", API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        ChatUserDTO chatUserDTO = ChatUserDTO.builder()
                .userId(userDTO.getEmail())
                .name(userDTO.getName())
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(chatUserDTO);

        HttpEntity<String> body = new HttpEntity<>(jsonBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        ResponseEntity<MemberResponseDTO> responseEntity = restTemplate.exchange(new URI(url), HttpMethod.POST, body, MemberResponseDTO.class);

        return responseEntity;
    }

    public ResponseEntity<MemberResponseDTO> deleteUser(User user) throws Exception {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(BASE_URL);
        urlBuilder.append("/members/");
        urlBuilder.append(user.getEmail());

        String url = urlBuilder.toString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "*/*");
        headers.set("x-project-id", PROJECT_ID);
        headers.set("x-api-key", API_KEY);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<MemberResponseDTO> responseEntity = restTemplate.exchange(new URI(url), HttpMethod.DELETE, requestEntity, MemberResponseDTO.class);
        System.out.println("responseEntity: " + responseEntity);
        return responseEntity;
    }
}
