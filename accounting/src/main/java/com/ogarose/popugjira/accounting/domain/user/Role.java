package com.ogarose.popugjira.accounting.domain.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_EMPLOYEE,
    ROLE_ACCOUNTANT,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
