package com.testcompany.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@ControllerAdvice(annotations = RestController.class)
@Component
public class RestApiErrorHandler {

    @ExceptionHandler({IOException.class, RuntimeException.class})
    public ResponseEntity<Exception> handleNotFoundException(final Exception exception)
    {
        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
