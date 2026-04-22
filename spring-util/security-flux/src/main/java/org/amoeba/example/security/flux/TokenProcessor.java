package org.amoeba.example.security.flux;

import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class TokenProcessor implements WebFilter {

    private final TokenRepo tokenRepo;

    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    public Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
            return chain.filter(exchange);
        }

        String jwt = authHeader.substring(TOKEN_PREFIX.length());

        return Mono.fromCallable(() -> tokenRepo.infoForToken(jwt))
                .flatMap(authInfo -> {
                    if (authInfo == null) {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    }

                    if(authInfo.getRoles() == null){
                        authInfo.setRoles(List.of());
                    }

                    List<? extends GrantedAuthority> authorities = authInfo.getRoles().stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList();

                    PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(
                            authInfo,
                            jwt,
                            authorities
                    );

                    return chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(authentication));
                });
    }

}
