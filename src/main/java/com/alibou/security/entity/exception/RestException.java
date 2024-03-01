package com.alibou.security.entity.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RestException extends RuntimeException {

    private final String message;

    private HttpStatus status = HttpStatus.BAD_REQUEST;

    private RestException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    private RestException(String message) {
        super(message);
        this.message = message;
    }

    public static RestException restThrow(String message) {
        return new RestException(message);
    }

    public static RestException restThrow(String message, HttpStatus status) {
        return new RestException(message, status);
    }
}
