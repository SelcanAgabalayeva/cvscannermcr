package com.selcan.upload_service.controller;


import com.selcan.upload_service.dtos.UploadResponseDTO;
import com.selcan.upload_service.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/uploads")
@RequiredArgsConstructor
public class UploadController {

    private final FileUploadService fileUploadService;

    @PostMapping("/cv")
    public ResponseEntity<UploadResponseDTO> upload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(fileUploadService.uploadZip(file));
    }
}