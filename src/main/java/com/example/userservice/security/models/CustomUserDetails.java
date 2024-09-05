package com.example.userservice.security.models;

import com.example.userservice.models.Role;
import com.example.userservice.models.User;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@JsonDeserialize
public class CustomUserDetails implements UserDetails {
    private Long userId;
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;
    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    public CustomUserDetails(){

    }

    public CustomUserDetails(User user) {

        this.username=user.getEmail();
        this.password=user.getHashedPassword();
        this.authorities=new ArrayList<GrantedAuthority>();
        for(Role role:user.getRoles()){
            authorities.add(new CustomGrantedAuthority(role));
        }
        this.enabled=true;
        this.accountNonExpired=true;
        this.accountNonLocked=true;
        this.credentialsNonExpired=true;
        this.userId=user.getId();
    }
    public Long getUserId() {
        return this.userId;
    }
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

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
