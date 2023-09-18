package com.tiep.commonservice.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@AllArgsConstructor
public class ValidateException extends RuntimeException{
    private final String code;
    private final Map<String, String> messageMap;
    private final HttpStatus status;
}
