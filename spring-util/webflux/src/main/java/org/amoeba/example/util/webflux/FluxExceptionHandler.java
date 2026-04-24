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

    @ExceptionHandler(ExampleException.class)
    public ResponseEntity<String> handleAllExceptions(ExampleException ex, ServerHttpRequest request) {

        LoggingEventBuilder loggingEventBuilder = log.atError()
                .setCause(ex);

        loggingEventBuilder = ex.addToLog(loggingEventBuilder);
        loggingEventBuilder.log("An unexpected error occurred: {}", ex.getMessage());

        return new ResponseEntity<>(ex.publicMessage(), HttpStatusCode.valueOf(ex.httpStatusCode()));
    }
}
