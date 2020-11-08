package org.example.app.security;

import org.springframework.security.core.GrantedAuthority;

public enum ERole implements GrantedAuthority {
    USER("Пользователь"),
    ADMIN("Админ");

    private final String value;

    ERole(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
