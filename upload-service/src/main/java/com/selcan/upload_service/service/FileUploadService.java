package com.selcan.upload_service.service;


import com.selcan.upload_service.dtos.UploadResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    UploadResponseDTO uploadZip(MultipartFile file);
}
