package com.ogarose.popugjira.domain.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_EMPLOYEE,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
