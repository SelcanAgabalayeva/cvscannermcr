package com.selcan.upload_service.repository;

import com.selcan.upload_service.dtos.UploadResponseDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

public interface UploadJobRepository extends JpaRepository<com.selcan.upload_service.entity.UploadJob, Long> {

}
