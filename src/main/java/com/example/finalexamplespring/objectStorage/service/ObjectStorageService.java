package com.example.finalexamplespring.objectStorage.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.example.finalexamplespring.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ObjectStorageService {
    private final AmazonS3 s3;
    @Value("${cloud.aws.s3.bucket.name}")
    private String bucket;


    public ResponseEntity<?> getObject(String objectName) {
        try {
            S3Object o = s3.getObject(new GetObjectRequest(bucket, objectName));
            S3ObjectInputStream objectInputStream = ((S3Object) o).getObjectContent();
            byte[] bytes = IOUtils.toByteArray(objectInputStream);

            String fileName = URLEncoder.encode(objectName, "UTF-8").replaceAll("\\+", "%20");
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            httpHeaders.setContentLength(bytes.length);
            httpHeaders.setContentDispositionFormData("attachment", fileName);

            return new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
        } catch (IOException e) {
            // IOException이 발생한 경우에는 오류 상태(400 Bad Request)와 오류 메시지를 포함한 응답을 클라이언트에게 반환
            return ResponseEntity.badRequest().body("Error occurred during file retrieval: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error occurred during file retrieval: " + e.getMessage());
        }
    }

    public ResponseEntity<?> deleteObject(String objectName) {
        try {
            s3.deleteObject(bucket, objectName);
            String message = "Object " + objectName + " has been deleted from " + bucket + ".";
            return ResponseEntity.ok(message);
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error occurred during file deletion: " + e.getMessage());
        } catch (SdkClientException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error occurred during file deletion: " + e.getMessage());
        }
    }

    public String uploadFile(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        PutObjectRequest putObjectRequest = null;
        try {
            putObjectRequest = new PutObjectRequest(bucket, uniqueFilename, multipartFile.getInputStream(), objectMetadata);
            s3.putObject(putObjectRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return uniqueFilename;
    }
}