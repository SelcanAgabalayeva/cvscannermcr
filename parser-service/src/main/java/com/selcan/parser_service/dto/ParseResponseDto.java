package com.selcan.parser_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParseResponseDto {

    private String fullName;
    private List<String> skills;
    private Integer experienceYears;
    private String location;
}
