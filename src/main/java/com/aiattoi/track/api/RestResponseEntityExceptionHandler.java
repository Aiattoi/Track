package com.aiattoi.track.api;

import com.aiattoi.track.business.EntityStateException;
import com.aiattoi.track.business.NoEntityFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityStateException.class)
    public ResponseEntity<Object> handleConflict(RuntimeException e, WebRequest request) {
        String response = "Entity already exists.";
        return handleExceptionInternal(e, response, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(NoEntityFoundException.class)
    public ResponseEntity<Object> handleNotFound(RuntimeException e, WebRequest request) {
        String response = e.getMessage();
        return handleExceptionInternal(e, response, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
