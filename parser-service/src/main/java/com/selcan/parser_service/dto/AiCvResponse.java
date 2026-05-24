package com.selcan.parser_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class AiCvResponse {
    private String fullName;
    private List<String> skills;
    private Integer experienceYears;
    private String location;
}
