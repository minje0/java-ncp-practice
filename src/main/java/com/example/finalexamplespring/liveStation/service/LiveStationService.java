package com.example.finalexamplespring.liveStation.service;

import com.example.finalexamplespring.liveStation.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;

@Service
@RequiredArgsConstructor
public class LiveStationService {
    @Value("${cloud.aws.s3.bucket.name}")
    String bucket;
    @Value("${cloud.ncp.access.key}")
    String accessKey;
    @Value("${cloud.ncp.secret.key}")
    String secretKey;
    String liveStationUrl = "https://livestation.apigw.ntruss.com/api/v2/channels";

    public ResponseEntity<?> createChannel(String name) {
        try {
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append(liveStationUrl);
            String url = urlBuilder.toString();
            String signUrl = url.substring(url.indexOf(".com") + 4);

            //요청 바디 만들기
            CdnDTO cdnDTO = CdnDTO.builder()
                    .createCdn(true)
                    .cdnType("CDN_PLUS")
                    .build();
            RecordDTO recordDTO = RecordDTO.builder()
                    .type("AUTO_UPLOAD")
                    .format("MP4")
                    .bucketName(bucket)
                    .filePath("/")
                    .accessControl("PRIVATE")
                    .build();
            LiveStationRequestDTO requestDTO = LiveStationRequestDTO.builder()
                    .channelName(name)
                    .cdn(cdnDTO)
                    .qualitySetId(1)
                    .useDvr(true)
                    .immediateOnAir(true)
                    .timemachineMin(360)
                    .record(recordDTO)
                    .isStreamFailOver(false)
                    .build();

                ObjectMapper objectMapper = new ObjectMapper();
                String jsonBody = objectMapper.writeValueAsString(requestDTO);

                String timestamp = String.valueOf(System.currentTimeMillis());
                String method = "POST";
                String sig = makeSignature(timestamp, method, signUrl);

                //요청 헤더 만들기
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("x-ncp-apigw-timestamp", timestamp);
                headers.set("x-ncp-iam-access-key", accessKey);
                headers.set("x-ncp-apigw-signature-v2", sig);
                headers.set("x-ncp-region_code", "KR");

                HttpEntity<String> body = new HttpEntity<>(jsonBody, headers);

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

                ResponseEntity<LiveStationResponseDTO> response = restTemplate.exchange(new URI(url), HttpMethod.POST, body, LiveStationResponseDTO.class);

                System.out.println("createChannel: " + response);

                return response;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error occurred during file deletion: " + e.getMessage());
        }

    }

    public ResponseEntity<?> getChannelInfo(String channelID) {
        try {
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append(liveStationUrl);
            urlBuilder.append("/");
            urlBuilder.append(channelID);
            String url = urlBuilder.toString();

            String signUrl = url.substring(url.indexOf(".com") + 4);

            String timestamp = String.valueOf(System.currentTimeMillis());
            String method = "GET";
            String sig = makeSignature(timestamp, method, signUrl);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-ncp-apigw-timestamp", timestamp);
            headers.set("x-ncp-iam-access-key", accessKey);
            headers.set("x-ncp-apigw-signature-v2", sig);
            headers.set("x-ncp-region_code", "KR");

            HttpEntity<LiveStationResponseDTO> httpEntity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<LiveStationResponseDTO> response = restTemplate.exchange(new URI(url), HttpMethod.GET, httpEntity, LiveStationResponseDTO.class);

            System.out.println("getChannelInfo: " + response);

            return response;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error occurred during file deletion: " + e.getMessage());
        }

    }

    public ResponseEntity<?> getServiceURL(String channelID) {
        try {
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append(liveStationUrl);
            urlBuilder.append("/");
            urlBuilder.append(channelID);
            urlBuilder.append("/serviceUrls?serviceUrlType=GENERAL");
            String url = urlBuilder.toString();

            String signUrl = url.substring(url.indexOf(".com") + 4);

            String timestamp = String.valueOf(System.currentTimeMillis());
            String method = "GET";
            String sig = makeSignature(timestamp, method, signUrl);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-ncp-apigw-timestamp", timestamp);
            headers.set("x-ncp-iam-access-key", accessKey);
            headers.set("x-ncp-apigw-signature-v2", sig);
            headers.set("x-ncp-region_code", "KR");

            HttpEntity<ServiceUrlDTO> httpEntity = new HttpEntity<>(headers);

            // HTTP 요청을 보내기 위해 RestTemplate 객체 생성
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<ServiceUrlDTO> response = restTemplate.exchange(new URI(url), HttpMethod.GET, httpEntity, ServiceUrlDTO.class);

            System.out.println("getServiceURL: " + response);

            return response;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error occurred during file deletion: " + e.getMessage());
        }
    }

    public ResponseEntity<?> deleteChannel(String channelID) {
        try {
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append(liveStationUrl);
            urlBuilder.append("/");
            urlBuilder.append(channelID);
            String url = urlBuilder.toString();

            String signUrl = url.substring(url.indexOf(".com") + 4);

            String timestamp = String.valueOf(System.currentTimeMillis());
            String method = "DELETE";
            String sig = makeSignature(timestamp, method, signUrl);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-ncp-apigw-timestamp", timestamp);
            headers.set("x-ncp-iam-access-key", accessKey);
            headers.set("x-ncp-apigw-signature-v2", sig);
            headers.set("x-ncp-region_code", "KR");

            HttpEntity<String> httpEntity = new HttpEntity<>(headers);

            // HTTP 요청을 보내기 위해 RestTemplate 객체 생성
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<LiveStationResponseDTO> response = restTemplate.exchange(new URI(url), HttpMethod.DELETE, httpEntity, LiveStationResponseDTO.class);

            System.out.println("deleteChannel: " + response);

            return response;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error occurred during file deletion: " + e.getMessage());
        }
    }

    public String makeSignature(String timestamp, String method, String signUrl) {
        try {
            String space = " ";
            String newLine = "\n";

            String message = new StringBuilder()
                    .append(method)
                    .append(space)
                    .append(signUrl)
                    .append(newLine)
                    .append(timestamp)
                    .append(newLine)
                    .append(accessKey)
                    .toString();

            SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);

            byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
            String encodeBase64String = Base64.encodeBase64String(rawHmac);

            return encodeBase64String;
        } catch (Exception e) {
            return e.getMessage();
        }

    }
}
