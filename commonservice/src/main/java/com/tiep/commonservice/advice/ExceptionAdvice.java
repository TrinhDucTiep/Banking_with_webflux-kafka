package com.tiep.commonservice.advice;

import com.tiep.commonservice.common.CommonException;
import com.tiep.commonservice.common.ErrorMessage;
import com.tiep.commonservice.common.ValidateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(Exception ex) {
        log.error("Unknown internal server error: " + ex.getMessage());
        log.error("Exception class: " + ex.getClass());
        log.error("Exception cause: " + ex.getCause());
        return new ResponseEntity<>(new ErrorMessage("9999", HttpStatus.INTERNAL_SERVER_ERROR, "Unknown internal server error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleCommonException(CommonException exception) {
        log.error(String.format("Common error: %s %s %s", exception.getCode(), exception.getStatus(), exception.getMessage()));
        return new ResponseEntity<>(new ErrorMessage(exception.getCode(), exception.getStatus(), exception.getMessage()), exception.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleValidationException(ValidateException ex) {
        return new ResponseEntity<>(ex.getMessageMap(), ex.getStatus());
    }

}
