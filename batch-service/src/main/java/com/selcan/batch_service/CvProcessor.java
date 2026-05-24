package com.selcan.batch_service;

import com.selcan.batch_service.dto.ParseResponseDto;
import com.selcan.batch_service.model.Candidate;
import com.selcan.batch_service.model.CvFile;
import org.apache.tika.Tika;
import org.springframework.batch.item.ItemProcessor;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CvProcessor implements ItemProcessor<CvFile, Candidate> {

    private final Tika tika = new Tika();
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Candidate process(CvFile item) throws Exception {

        File file = new File(item.getFilePath());
        String text = tika.parseToString(file);

        ParseResponseDto dto = callParserService(text);

        return Candidate.builder()
                .fullName(dto.getFullName())
                .skills(dto.getSkills())
                .experienceYears(dto.getExperienceYears())
                .location(dto.getLocation())
                .build();
    }

    private ParseResponseDto callParserService(String text) {

        String url = "http://parser-service:8084/api/parser/parse";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("text", text);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<ParseResponseDto> response =
                restTemplate.postForEntity(url, request, ParseResponseDto.class);

        return response.getBody();
    }
}