package org.example.test.util;

import org.example.PublicRestEndpointApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class TestApplications {

    public SpringApplicationBuilder publicRest() {
        return new SpringApplicationBuilder(PublicRestEndpointApplication.class)
                .properties("server.port=8081")
                .profiles("local");
    }
}
