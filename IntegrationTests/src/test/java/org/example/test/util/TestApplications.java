package org.example.test.util;

import org.example.PublicRestEndpointApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class TestApplications {

    public static SpringApplicationBuilder publicRest(int mysqlPort) {
        return new SpringApplicationBuilder(PublicRestEndpointApplication.class)
                .properties("server.port=8080",
                        "logging.level.org.springframework.core.env.PropertySourcesPropertyResolver=TRACE",
                        "mysql.port=" + mysqlPort)
                .profiles("integration");
    }
}
