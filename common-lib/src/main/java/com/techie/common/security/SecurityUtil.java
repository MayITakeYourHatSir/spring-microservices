package com.techie.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SecurityUtil {

    public Jwt getCurrentJwt() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null) {
            throw new IllegalStateException("Authentication not found.");
        }

        if (!(authentication.getPrincipal() instanceof Jwt jwt)) {
            throw new IllegalStateException("Current principal is not Jwt.");
        }

        return jwt;
    }

    public Long getCurrentUserId() {

        return getCurrentJwt().getClaim("userId");
    }

    public String getCurrentUsername() {

        return getCurrentJwt().getClaimAsString("username");
    }

    public String getCurrentEmail() {

        return getCurrentJwt().getClaimAsString("email");
    }

    public List<String> getCurrentRoles() {

        return getCurrentJwt().getClaimAsStringList("roles");
    }

    public boolean hasRole(String role) {

        List<String> roles = getCurrentRoles();

        return roles != null && roles.contains(role);
    }

}
