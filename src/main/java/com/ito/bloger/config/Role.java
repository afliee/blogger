package com.ito.bloger.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@AllArgsConstructor
@Getter
public enum Role {
    ADMIN("ADMIN")
    , USER("USER");
    private final String name;
    public SimpleGrantedAuthority authority() {
        return new SimpleGrantedAuthority("ROLE_" + this.name());
    }
}
