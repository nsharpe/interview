package org.example.admin.config;

import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class SecurityConfig implements WebClientCustomizer {

    @Override
    public void customize(WebClient.Builder webClientBuilder) {
        webClientBuilder.filter((request, next) ->
                ReactiveSecurityContextHolder.getContext()
                        .map(SecurityContext::getAuthentication)
                        .filter(auth -> auth.getCredentials() != null)
                        .map(auth -> auth.getCredentials().toString()) // Assuming token is in credentials
                        .defaultIfEmpty("default_or_empty")
                        .flatMap(token -> {
                            ClientRequest newRequest = ClientRequest.from(request)
                                    .header("Authorization", "Bearer " + token)
                                    .build();
                            return next.exchange(newRequest);
                        })
        );
    }
}
