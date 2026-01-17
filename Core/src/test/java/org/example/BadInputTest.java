package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BadInputTest {

    @Test
    void test(){
        BadInput badInput = new BadInput("field", "value", "reason");

        assertEquals("field",badInput.getField());
        assertEquals("value",badInput.getValue());
        assertEquals("reason",badInput.getReason());
    }
}
