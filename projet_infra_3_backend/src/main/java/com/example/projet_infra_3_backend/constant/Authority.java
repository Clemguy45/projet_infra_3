package com.example.projet_infra_3_backend.constant;

public class Authority {
    public static final String[] USER_AUTHORITIES = {"user:read"};
    public static final String[] ADMIN_AUTHORITIES = {"user:read", "user:create" , "user:update", "user:delete"};
}
