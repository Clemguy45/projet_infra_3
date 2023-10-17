package com.example.projet_infra_3_backend.config;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthorizationFilter extends UsernamePasswordAuthenticationFilter {

}
