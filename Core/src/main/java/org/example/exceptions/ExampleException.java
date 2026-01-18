package org.example.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.spi.LoggingEventBuilder;

@Slf4j
public abstract class ExampleException extends RuntimeException{

    public ExampleException() {
    }

    public ExampleException(String message) {
        super(message);
    }

    public ExampleException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExampleException(Throwable cause) {
        super(cause);
    }

    public int httpStatusCode(){
        return 500;
    }

    public String publicMessage(){
        return "An internal server error occurred";
    }

    public LoggingEventBuilder addToLog(LoggingEventBuilder loggingEventBuilder){
        return loggingEventBuilder;
    }
}
