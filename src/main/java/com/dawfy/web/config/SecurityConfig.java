package com.dawfy.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dawfy.enums.Roles;
import com.dawfy.services.UserSecurityService;

@Configuration
public class SecurityConfig {

        @Bean
        public JwtFilter jwtFilter() {
                return new JwtFilter();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {

                http
                                .csrf(csrf -> csrf.disable())
                                .cors(cors -> cors.configurationSource(
                                                request -> new org.springframework.web.cors.CorsConfiguration()
                                                                .applyPermitDefaultValues()))
                                .sessionManagement(management -> management
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(requests -> requests
                                                .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                                                .requestMatchers(HttpMethod.GET,
                                                                "/artista/**",
                                                                "/album/**",
                                                                "/cancion/**",
                                                                "/categoria/**",
                                                                "/colaboracion/**")
                                                .hasRole(Roles.CLIENTE.name())
                                                .requestMatchers(
                                                                "/artista/**",
                                                                "/album/**",
                                                                "/cancion/**",
                                                                "/categoria/**",
                                                                "/colaboracion/**")
                                                .hasRole(Roles.ARTISTA.name())
                                                .anyRequest().hasRole(Roles.ADMIN.name()))
                                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
                return configuration.getAuthenticationManager();
        }
}