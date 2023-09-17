package com.tiep.commonservice.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ErrorMessage {
    private String code;
    private HttpStatus status;
    private String message;
}
