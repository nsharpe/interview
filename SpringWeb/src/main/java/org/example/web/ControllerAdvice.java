package org.example.web;

import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.ExampleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@org.springframework.web.bind.annotation.ControllerAdvice
@Slf4j
public class ControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);

        HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "An internal server error occurred";

        if(ex instanceof ExampleException reportableException){
            status = HttpStatusCode.valueOf(reportableException.httpStatusCode());
            message = reportableException.publicMessage();
        }

        return new ResponseEntity<>(message, status);
    }
}
