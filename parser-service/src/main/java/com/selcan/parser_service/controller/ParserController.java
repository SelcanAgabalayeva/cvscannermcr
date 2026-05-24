package com.selcan.parser_service.controller;

import com.selcan.parser_service.dto.ParseRequestDto;
import com.selcan.parser_service.dto.ParseResponseDto;
import com.selcan.parser_service.service.ParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/parser")
@RequiredArgsConstructor
public class ParserController {

    private final ParserService parserService;

    @PostMapping("/parse")
    public ParseResponseDto parse(@RequestBody ParseRequestDto dto) {
        return parserService.parse(dto.getText());
    }
}
