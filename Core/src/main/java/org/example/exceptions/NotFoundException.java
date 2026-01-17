package org.example.exceptions;

public class NotFoundException extends ExampleException {

    public NotFoundException(String msg) {
        super(msg);
    }

    @Override
    public int httpStatusCode() {
        return 404;
    }

    @Override
    public String publicMessage() {
        return getMessage();
    }
}
