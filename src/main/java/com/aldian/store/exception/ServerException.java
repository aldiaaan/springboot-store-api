package com.aldian.store.exception;

import lombok.Getter;

@Getter
public class ServerException extends RuntimeException {
    private final String code;

    public ServerException(String code, String message) {
        super(message);
        this.code = code;
    }

    public ServerException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}