package com.vote.votemanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class AuthConfig {

    @Autowired
    public SecurityFilter securityFilter;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/users/**").hasAnyAuthority("VOTER", "ADMIN")
                        .requestMatchers("/api/v1/elections/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/polls/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/admins/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/candidates/**").hasAnyAuthority("ADMIN","CANDIDATE")
                        .requestMatchers("/api/v1/votes/**").hasAnyAuthority("ADMIN","VOTER","CANDIDATE")
                        .requestMatchers("/api/v1/results/**").hasAnyAuthority("ADMIN")

                        .anyRequest().authenticated())

                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}