package com.ogarose.popugjira.auth.model;

import org.springframework.security.core.GrantedAuthority;

public enum Roles implements GrantedAuthority {
    ROLE_EMPLOYEE, ROLE_ACCOUNTANT, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
