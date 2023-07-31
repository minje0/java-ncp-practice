package com.example.finalexamplespring.cdn.service;

import com.example.finalexamplespring.cdn.dto.CdnPlusInstanceListResponseDTO;
import org.apache.tomcat.util.codec.binary.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;

@RequiredArgsConstructor
@Service
public class CdnService {
    @Value("${cloud.ncp.access.key}")
    private String accessKey;
    @Value("${cloud.ncp.secret.key}")
    private String secretKey;
    private String cdnURL = "https://ncloud.apigw.ntruss.com/cdn/v2";

    public ResponseEntity<?> getCdnPlusInstanceList(String cdnInstanceNo){
        try {
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append(cdnURL);
            urlBuilder.append("/");
            urlBuilder.append("getCdnPlusInstanceList?cdnInstanceNo=");
            urlBuilder.append(cdnInstanceNo);
            urlBuilder.append("&responseFormatType=XML");

            String url = urlBuilder.toString();

            String signUrl = url.substring(url.indexOf(".com") + 4);

            String timestamp = String.valueOf(System.currentTimeMillis());
            String method = "GET";
            String sig = makeSignature(timestamp, method, signUrl);

            HttpHeaders headers = new HttpHeaders();
            headers.set("x-ncp-apigw-timestamp", timestamp);
            headers.set("x-ncp-iam-access-key", accessKey);
            headers.set("x-ncp-apigw-signature-v2", sig);

            HttpEntity<CdnPlusInstanceListResponseDTO> httpEntity = new HttpEntity<>(headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<CdnPlusInstanceListResponseDTO> response = restTemplate.exchange(new URI(url), HttpMethod.GET, httpEntity, CdnPlusInstanceListResponseDTO.class);

            System.out.println("CdnPlusInstanceListResponseDTO: " + response);

            return response;
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error occurred during file retrieval: " + e.getMessage());
        }

    }

    public String makeSignature(String timestamp, String method, String signUrl){
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
