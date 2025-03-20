package com.course.springboot.exceptions;

import org.springframework.http.HttpStatus;

public class RegraDeNegocioException extends Exception{
    private final HttpStatus status;

    public RegraDeNegocioException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
