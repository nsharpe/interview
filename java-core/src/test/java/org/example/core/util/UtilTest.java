package org.example.core.util;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.example.core.util.Util.OBJECT_MAPPER;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UtilTest {

    @Test
    void testTimeStampSerialize() throws Exception{
          String json = OBJECT_MAPPER.writeValueAsString(new TestSerialize(OffsetDateTime.of(2026,1,23,1,1,1,1, ZoneOffset.UTC)));

          assertEquals("{\"val\":\"2026-01-23T01:01:01.000000001Z\"}",json);
    }

    public record TestSerialize(Object val){}

    public record TestDeserialize(String val){}
}