package com.selcan.batch_service;
import com.selcan.batch_service.model.CvFile;
import jakarta.annotation.PostConstruct;
import org.springframework.batch.item.ItemReader;
import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
public class CvFileReader implements ItemReader<CvFile> {

    @Value("${file.upload-dir}")
    private String uploadDir;

    private List<File> files = new ArrayList<>();
    private int index = 0;

    @PostConstruct
    public void init() {

        File folder = new File(uploadDir + "extracted/");

        System.out.println("LOOKING DIR: " + folder.getAbsolutePath());

        File[] fileArray = folder.listFiles();

        if (fileArray != null) {
            files = Arrays.asList(fileArray);
        }

        System.out.println("FILES FOUND: " + files.size());
    }

    @Override
    public CvFile read() {
        if (index >= files.size()) return null;

        return new CvFile(files.get(index++).getAbsolutePath());
    }
}