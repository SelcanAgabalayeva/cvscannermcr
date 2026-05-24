package com.selcan.upload_service.service.impls;

import com.selcan.upload_service.dtos.UploadResponseDTO;
import com.selcan.upload_service.enums.UploadStatus;
import com.selcan.upload_service.repository.UploadJobRepository;
import com.selcan.upload_service.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    private final UploadJobRepository uploadJobRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public UploadResponseDTO uploadZip(MultipartFile file) {

        com.selcan.upload_service.entity.UploadJob job = com.selcan.upload_service.entity.UploadJob.builder()
                .fileName(file.getOriginalFilename())
                .fileSize(file.getSize())
                .status(UploadStatus.UPLOADED)
                .uploadedAt(LocalDateTime.now())
                .build();

        job = uploadJobRepository.save(job);

        try {
            Path zipPath = Paths.get(uploadDir, file.getOriginalFilename());
            Files.createDirectories(zipPath.getParent());

            Files.copy(file.getInputStream(), zipPath, StandardCopyOption.REPLACE_EXISTING);

            job.setFilePath(zipPath.toString());
            job.setStatus(UploadStatus.EXTRACTING);
            uploadJobRepository.save(job);

            unzip(zipPath.toString(), uploadDir + "extracted/");

            job.setStatus(UploadStatus.PROCESSED);
            job.setProcessedAt(LocalDateTime.now());
            uploadJobRepository.save(job);

            return new UploadResponseDTO(
                    job.getId(),
                    "Upload successful",
                    "SUCCESS"
            );

        } catch (Exception e) {

            job.setStatus(UploadStatus.FAILED);
            uploadJobRepository.save(job);

            return new UploadResponseDTO(
                    job.getId(),
                    "Upload failed: " + e.getMessage(),
                    "FAILED"
            );
        }
    }

    private void unzip(String zipFilePath, String destDir) throws IOException {

        File dir = new File(destDir);
        if (!dir.exists()) dir.mkdirs();

        byte[] buffer = new byte[1024];

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {

            ZipEntry zipEntry = zis.getNextEntry();

            while (zipEntry != null) {

                File newFile = new File(destDir, zipEntry.getName());

                String destDirPath = new File(destDir).getCanonicalPath();
                String destFilePath = newFile.getCanonicalPath();

                if (!destFilePath.startsWith(destDirPath + File.separator)) {
                    throw new IOException("Entry is outside target dir");
                }

                new File(newFile.getParent()).mkdirs();

                try (FileOutputStream fos = new FileOutputStream(newFile)) {

                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }

                zipEntry = zis.getNextEntry();
            }

            zis.closeEntry();
        }
    }
}
