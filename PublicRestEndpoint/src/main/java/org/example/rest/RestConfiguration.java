package org.example.rest;

import io.micrometer.core.instrument.MeterRegistry;
import org.example.repository.UserRepository;
import org.example.service.user.UserService;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class RestConfiguration
{
    @Bean
    public UserService userService(UserRepository repository){
        return new UserService(repository);
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("region", "dev");
    }
}
