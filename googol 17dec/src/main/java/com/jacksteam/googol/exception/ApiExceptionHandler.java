package com.jacksteam.googol.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler{

    @ExceptionHandler(value ={ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){
        // Create a payload containing details of exception
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException = new ApiException(e.getMessage(), badRequest, ZonedDateTime.now(ZoneId.of("Z")));
        // Return a response entity
        return new ResponseEntity<>(apiException,badRequest);
    }

}