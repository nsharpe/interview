package org.amoeba.example.util.webflux;

import lombok.extern.slf4j.Slf4j;
import org.amoeba.example.core.exceptions.ExampleException;
import org.slf4j.spi.LoggingEventBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class FluxExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex, ServerHttpRequest request) {

        HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "An internal server error occurred";

        LoggingEventBuilder loggingEventBuilder = log.atError()
                .setCause(ex);

        if(ex instanceof ExampleException reportableException){
            status = HttpStatusCode.valueOf(reportableException.httpStatusCode());
            message = reportableException.publicMessage();
            loggingEventBuilder = reportableException.addToLog(loggingEventBuilder);
        }

        loggingEventBuilder.log("An unexpected error occurred: {}", ex.getMessage());

        return new ResponseEntity<>(message, status);
    }
}
