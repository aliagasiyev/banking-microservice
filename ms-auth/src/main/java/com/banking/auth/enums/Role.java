package com.banking.auth.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN,
    TELLER,
    AUDITOR,
    LOAN_MANAGER,
    SUPER_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
