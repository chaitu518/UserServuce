package com.example.userservice.security.models;

import com.example.userservice.models.Role;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;
@JsonDeserialize
public class CustomGrantedAuthority implements GrantedAuthority {
    private String authority;
    public CustomGrantedAuthority() {

    }
    public CustomGrantedAuthority(Role role) {
        this.authority=role.getTitle();
    }
    @Override
    public String getAuthority() {
        return authority;
    }
}
