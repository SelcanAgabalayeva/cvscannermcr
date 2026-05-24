package com.selcan.upload_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadResponseDTO {
    private Long jobId;
    private String message;
    private String status;
}
