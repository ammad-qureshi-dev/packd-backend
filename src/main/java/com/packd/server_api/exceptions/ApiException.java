/* (C) 2026
Cash Money App */
package com.packd.server_api.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
public class ApiException extends RuntimeException {

    @Getter
    private final ExceptionCode exceptionCode;

    @Getter
    private final String message;

    @Getter
    private final HttpStatus status;

    @Getter
    private final ExceptionType exceptionType;

    @Getter
    private final String url;

    public ApiException(ExceptionCode exceptionCode, String message, HttpStatus status, ExceptionType exceptionType) {
        super();
        this.exceptionCode = exceptionCode;
        this.message = message;
        this.status = status;
        this.exceptionType = exceptionType;
        this.url = "";

    }

    public ApiException(ExceptionCode exceptionCode, String message, String url) {
        super();
        this.exceptionCode = exceptionCode;
        this.message = message;
        this.url = url;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.exceptionType = ExceptionType.ERROR;
    }

    public ApiException(ExceptionCode exceptionCode, String message, HttpStatus status) {
        super();
        this.exceptionCode = exceptionCode;
        this.message = message;
        this.status = status;
        this.exceptionType = ExceptionType.ERROR;
        this.url = "";
    }

    public ApiException(ExceptionCode exceptionCode, String message, ExceptionType exceptionType) {
        super();
        this.exceptionCode = exceptionCode;
        this.message = message;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.exceptionType = exceptionType;
        this.url = "";
    }

    public ApiException(ExceptionCode exceptionCode, String message) {
        super();
        this.exceptionCode = exceptionCode;
        this.message = message;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.exceptionType = ExceptionType.ERROR;
        this.url = "";
    }

}
