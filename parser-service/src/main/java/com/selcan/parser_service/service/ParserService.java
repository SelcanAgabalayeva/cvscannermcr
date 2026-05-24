package com.selcan.parser_service.service;

import com.selcan.parser_service.dto.ParseResponseDto;

public interface ParserService {
    ParseResponseDto parse(String text);
}
