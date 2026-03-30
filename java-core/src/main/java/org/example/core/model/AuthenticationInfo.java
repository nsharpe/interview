package org.example.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String token;
    private List<String> roles;
}
