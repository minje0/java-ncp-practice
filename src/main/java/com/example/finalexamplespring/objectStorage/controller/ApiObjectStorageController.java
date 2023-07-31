package com.example.finalexamplespring.objectStorage.controller;

import com.example.finalexamplespring.objectStorage.service.ObjectStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/storage")
@RequiredArgsConstructor
@RestController
public class ApiObjectStorageController {
    private final ObjectStorageService storageService;

    @GetMapping("/download/{objectName}")
    public ResponseEntity<?> downObject(@PathVariable String objectName) {
        return storageService.getObject(objectName);
    }

    @DeleteMapping("/delete/{objectName}")
    public ResponseEntity<?> deleteObject(@PathVariable String objectName) {
        return storageService.deleteObject(objectName);
    }

    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        String uploadedFileName = storageService.uploadFile(file);
        return ResponseEntity.ok("File uploaded successfully. S3 key: " + uploadedFileName);
    }
}
