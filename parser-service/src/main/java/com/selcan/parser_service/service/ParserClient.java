package com.selcan.parser_service.service;

import com.selcan.parser_service.dto.ParseRequestDto;
import com.selcan.parser_service.dto.ParseResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "PARSER-SERVICE")
public interface ParserClient {

    @PostMapping("/api/parser/parse")
    ParseResponseDto parse(ParseRequestDto dto);
}
