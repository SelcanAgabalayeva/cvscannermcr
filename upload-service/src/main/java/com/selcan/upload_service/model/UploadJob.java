package com.selcan.upload_service.entity;

import com.selcan.upload_service.enums.UploadStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "upload_jobs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private Long fileSize;
    private String filePath;

    @Enumerated(EnumType.STRING)
    private UploadStatus status;

    private LocalDateTime uploadedAt;
    private LocalDateTime processedAt;
}