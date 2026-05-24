package com.selcan.batch_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParseResponseDto {

    private String fullName;
    private String skills;
    private Integer experienceYears;
    private String location;
}