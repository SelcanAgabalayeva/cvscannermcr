package com.selcan.upload_service.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadRequestDTO {
    private MultipartFile file;
}