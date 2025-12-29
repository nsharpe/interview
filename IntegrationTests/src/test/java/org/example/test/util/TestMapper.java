package org.example.test.util;


import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestMapper {

    public final static JsonMapper MAPPER = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();


    private TestMapper() {}
}
