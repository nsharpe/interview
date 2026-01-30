package org.example.qa.config;

import org.example.core.model.AuthenticationInfo;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientConfiguration implements WebClientCustomizer {

    @Override
    public void customize(WebClient.Builder webClientBuilder) {
        webClientBuilder.filter((request, next) -> {
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

                    if (auth != null && auth.getPrincipal() instanceof AuthenticationInfo info) {
                        ClientRequest newRequest = ClientRequest.from(request)
                                .header("Authorization", "Bearer " + info.getToken())
                                .build();
                        return next.exchange(newRequest);
                    }
                    return next.exchange(request);
                }
        );
    }
}
