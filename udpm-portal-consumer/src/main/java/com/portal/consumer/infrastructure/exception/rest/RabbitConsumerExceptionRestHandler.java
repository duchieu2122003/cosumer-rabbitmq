package com.portal.consumer.infrastructure.exception.rest;

import com.portal.consumer.infrastructure.exception.RabbitConsumerExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author phongtt35
 */

public abstract class RabbitConsumerExceptionRestHandler<Z extends Exception>
        extends RabbitConsumerExceptionHandler<ResponseEntity<?>,Z> {

    @Override
    protected ResponseEntity<?> wrap(Z ex) {
        return new ResponseEntity<>(wrapApi(ex), HttpStatus.BAD_REQUEST);
    }

    protected abstract Object wrapApi(Z ex);
}
