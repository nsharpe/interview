package org.example.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private UUID userId;
    private List<String> roles;

    public static AuthenticationInfo get(){
        return (AuthenticationInfo) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
