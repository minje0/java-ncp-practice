package com.example.finalexamplespring.user.controller;

import com.example.finalexamplespring.dto.ResponseDTO;
import com.example.finalexamplespring.user.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@RestController
public class JoinController {
    private final JoinService joinService;

    @GetMapping("/user/sendEmail")
    public ResponseEntity<?> sendMail(@RequestParam("email") String email) {
        String authCode = joinService.mailSend(email);

        ResponseDTO<String> responseDTO = new ResponseDTO<>();

        if (authCode != null && !authCode.equals("")) {
            responseDTO.setItem(authCode);
            responseDTO.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("인증 코드 생성 및 이메일 전송 실패");
        }
    }

    @GetMapping("/map/{address}")
    public void showMap(@PathVariable("address") String address) {
        String apiKey = "1B5047DB-E0E8-34A5-856E-2E6118507460";
        String service = "address";
        String request = "getcoord";
        String version = "2.0";
        String crs = "epsg:4326";
        boolean refine = true;
        boolean simple = false;
        String format = "Json";
        String type = "road";

        StringBuilder urlBuilder = new StringBuilder("https://api.vworld.kr/req/address?");
        urlBuilder.append("service=").append(service)
                .append("&request=").append(request)
                .append("&version=").append(version)
                .append("&crs=").append(crs)
                .append("&address=").append(address)
                .append("&refine=").append(refine)
                .append("&simple=").append(simple)
                .append("&format=").append(format)
                .append("&type=").append(type)
                .append("&key=").append(apiKey);

        String url = urlBuilder.toString();

        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // HTTP GET request
        String response = restTemplate.getForObject(url, String.class);

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);

        // Print the response
        System.out.println("Response data:");
        System.out.println(responseEntity.getBody());
    }
}
