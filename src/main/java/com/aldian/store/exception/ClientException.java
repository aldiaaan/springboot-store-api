package com.aldian.store.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ClientException extends RuntimeException {
    private final String code;
    private final HttpStatus status;

    public ClientException(String code, String message, HttpStatus status) {
        super(message);
        this.code = code;
        this.status = status;
    }
}