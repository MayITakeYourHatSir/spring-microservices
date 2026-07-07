package com.techie.authservice.model.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public record UserPrincipal(Long id, String username, String email, String password,
                            Collection<? extends GrantedAuthority> authorities) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
