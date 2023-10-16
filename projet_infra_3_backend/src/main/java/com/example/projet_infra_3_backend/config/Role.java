package com.example.projet_infra_3_backend.config;

import static com.example.projet_infra_3_backend.constant.Authority.ADMIN_AUTHORITIES;
import static com.example.projet_infra_3_backend.constant.Authority.USER_AUTHORITIES;

public enum Role {
    ROLE_USER(USER_AUTHORITIES),
    ROLE_ADMIN(ADMIN_AUTHORITIES);


    private String[] authorities;

    Role(String... authorities){
        this.authorities = authorities;
    }

    public String[] getAuthorities() {
        return authorities;
    }
}
