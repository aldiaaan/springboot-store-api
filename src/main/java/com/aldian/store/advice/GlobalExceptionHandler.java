package com.aldian.store.advice;

import com.aldian.store.dto.ErrorDetail;
import com.aldian.store.exception.ClientException;
import com.aldian.store.exception.ServerException;
import com.aldian.store.service.CommonApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<CommonApiResponse<ErrorDetail>> handleClientException(ClientException ex) {
        ErrorDetail detail = new ErrorDetail(ex.getCode(), ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(new CommonApiResponse<>("error", detail));
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<CommonApiResponse<ErrorDetail>> handleServerException(ServerException ex) {
        log.error("Server Error [{}]: ", ex.getCode(), ex);

        ErrorDetail detail = new ErrorDetail(ex.getCode(), "An unexpected internal server error occurred. Please try again later");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CommonApiResponse<>("error", detail));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonApiResponse<ErrorDetail>> handleGenericException(Exception ex) {
        log.error("Unhandled System Exception: ", ex);

        ErrorDetail detail = new ErrorDetail("ERR-67", "An unexpected internal server error occurred");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CommonApiResponse<>("error", detail));
    }
}