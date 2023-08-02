package com.example.Task.management.exception;

import com.example.Task.management.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


import java.util.Date;
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskAPIException.class)
    public ResponseEntity<ErrorDetails> handleTodoAPIException(TaskAPIException exception,
                                                               WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}