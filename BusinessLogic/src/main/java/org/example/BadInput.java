package org.example;


public class BadInput extends RuntimeException{

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

    public String getField() {
        return field;
    }

    public String getValue() {
        return value;
    }

    public String getReason() {
        return reason;
    }

    private static String toString(String field, String value, String reason){
        //StringBuilder is called behind the scenes so this is not computationally expensive
        return "field:{" + field + "}, value:{" + value + "}, reason:{"+ reason +"}";
    }
}
