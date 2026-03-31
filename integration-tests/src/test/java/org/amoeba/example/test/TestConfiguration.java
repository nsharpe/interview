package org.amoeba.example.test;

import org.amoeba.example.media.management.sdk.configuration.MediaManagementConfiguration;
import org.amoeba.example.publicrest.sdk.PublicRestSdkConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {MediaManagementConfiguration.class, PublicRestSdkConfig.class})
public class TestConfiguration {
}
