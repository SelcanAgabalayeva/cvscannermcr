package com.selcan.parser_service.service.impls;

import com.selcan.parser_service.dto.CandidateRequest;
import com.selcan.parser_service.dto.ParseResponseDto;
import com.selcan.parser_service.service.ParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.*;
@Service
@RequiredArgsConstructor
public class ParserServiceImpl implements ParserService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();


    @Override
    public ParseResponseDto parse(String text) {

        try {
            String prompt = buildPrompt(text);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> part = Map.of("text", prompt);
            Map<String, Object> content = Map.of("parts", List.of(part));

            Map<String, Object> request = Map.of("contents", List.of(content));

            HttpEntity<Map<String, Object>> entity =
                    new HttpEntity<>(request, headers);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(
                            apiUrl + "?key=" + apiKey,
                            entity,
                            String.class
                    );

            ParseResponseDto dto = extract(response.getBody());

            // 🔥 BURADA candidate-service-ə göndərirsən
            sendToCandidateService(dto);

            return dto;

        } catch (Exception e) {
            throw new RuntimeException("AI parsing failed: " + e.getMessage(), e);
        }
    }
    private void sendToCandidateService(ParseResponseDto dto) {

        CandidateRequest req = new CandidateRequest();

        req.setFullName(dto.getFullName());
        req.setLocation(dto.getLocation());
        req.setExperienceYears(dto.getExperienceYears());

        req.setSkills(String.join(", ", dto.getSkills()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CandidateRequest> entity =
                new HttpEntity<>(req, headers);

        restTemplate.postForEntity(
                "http://candidate-service:8085/api/candidates",
                entity,
                Void.class
        );
    }

    private String buildPrompt(String text) {
        return """
You are a STRICT CV parser.

CRITICAL RULES:
- fullName is ALWAYS the FIRST PERSON NAME in the CV
- The first line or header contains the full name
- DO NOT leave fullName empty if a name exists
- Extract name even if mixed with contacts
- DO NOT guess experienceYears
- DO NOT hallucinate

OUTPUT FORMAT (ONLY JSON):
{
  "fullName": "",
  "skills": [],
  "experienceYears": 0,
  "location": ""
}

CV TEXT:
""" + text;
    }

    private ParseResponseDto extract(String body) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(body);

        String content = root.path("candidates")
                .get(0)
                .path("content")
                .path("parts")
                .get(0)
                .path("text")
                .asText();

        content = content.replace("```json", "").replace("```", "").trim();

        return mapper.readValue(content, ParseResponseDto.class);
    }
}