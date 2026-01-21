package org.example.test;

import org.example.media.management.sdk.configuration.MediaManagementConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {MediaManagementConfiguration.class})
public class TestConfiguration {
}
