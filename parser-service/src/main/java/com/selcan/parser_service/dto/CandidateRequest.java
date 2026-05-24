package com.selcan.parser_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CandidateRequest {
    private String fullName;
    private String skills;
    private Integer experienceYears;
    private String location;
}
