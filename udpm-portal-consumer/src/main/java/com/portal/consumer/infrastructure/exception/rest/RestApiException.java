package com.portal.consumer.infrastructure.exception.rest;

/**
 * @author nguyenvv4
 */
public class RestApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    public RestApiException() {
    }

    public RestApiException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
