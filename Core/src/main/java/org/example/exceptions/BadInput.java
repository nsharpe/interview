package org.example.exceptions;


import lombok.Getter;

@Getter
public class BadInput extends ExampleException{

    private final String field;
    private final String value;
    private final String reason;


    public BadInput(String field,
                    String value,
                    String reason) {
        super(toString(field,value,reason));
        this.field = field;
        this.value = value;
        this.reason = reason;
    }

    private static String toString(String field, String value, String reason){
        //StringBuilder is called behind the scenes so this is not computationally expensive
        return "field:{" + field + "}, value:{" + value + "}, reason:{"+ reason +"}";
    }

    @Override
    public String publicMessage() {
        return getMessage();
    }

    @Override
    public int httpStatusCode() {
        return 400;
    }
}
