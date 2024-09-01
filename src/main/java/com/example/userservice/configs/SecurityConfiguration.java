package com.example.userservice.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http.authorizeHttpRequests(request ->{
            try {
                request.anyRequest().permitAll()
                        .and().cors().disable()
                        .csrf().disable();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
                });

             return http.build();
    }
}