package org.example.exceptions;

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
}
