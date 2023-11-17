package com.portal.consumer.infrastructure.exception.rest;

import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author phongtt35
 */
@RestControllerAdvice
public final class UnknownExceptionRestHandler extends
        RabbitConsumerExceptionRestHandler<Exception> {

    @Override
    protected Object wrapApi(Exception ex) {
        return ex.getMessage();
    }
}
