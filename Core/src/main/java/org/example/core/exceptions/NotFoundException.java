package org.example.core.exceptions;

import org.slf4j.spi.LoggingEventBuilder;

import java.util.UUID;

public class NotFoundException extends ExampleException {
    private final String recordName;
    private final String id;

    public NotFoundException(String msg) {
        super(msg);
        this.recordName = null;
        this.id = null;
    }

    public NotFoundException(String recordName, long id) {
        this(recordName,String.valueOf(id));
    }

    public NotFoundException(String recordName, UUID id) {
        this(recordName,id.toString());
    }

    public NotFoundException(String recordName, String id) {
        super(recordName + " not found id=" + id);
        this.recordName = recordName;
        this.id = id;
    }

    @Override
    public int httpStatusCode() {
        return 404;
    }

    @Override
    public String publicMessage() {
        return getMessage();
    }

    @Override
    public LoggingEventBuilder addToLog(LoggingEventBuilder loggingEventBuilder) {
        return loggingEventBuilder.addKeyValue("record",recordName)
                .addKeyValue("id",id);

    }
}
