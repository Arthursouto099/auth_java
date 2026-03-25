package com.cwi.poc.exceptions;

import org.springframework.http.HttpStatusCode;

public class BaseException extends RuntimeException {

    private final HttpStatusCode httpStatusCode;

    public BaseException(String message, HttpStatusCode code) {
        super(message);
        this.httpStatusCode = code;
    }

    public HttpStatusCode getHttpStatusCode() {
        return this.httpStatusCode;
    }

    public Integer getHttpStatusCodeToInteger() {
        return this.httpStatusCode.value();
    }
}
