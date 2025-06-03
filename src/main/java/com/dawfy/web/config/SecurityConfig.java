package com.dawfy.web.config;

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
import org.springframework.web.cors.CorsConfigurationSource;

import com.dawfy.enums.Roles;

@Configuration
public class SecurityConfig {

        @Bean
        public JwtFilter jwtFilter() {
                return new JwtFilter();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter,
                        CorsConfigurationSource corsConfigurationSource) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                                .sessionManagement(management -> management
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authorizeHttpRequests(requests -> requests
                                                .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                                                // Permitir acceso a todas las rutas de Swagger sin autenticación
                                                .requestMatchers("/swagger-ui.html", "/swagger-ui/**",
                                                                "/swagger-ui/index.html",
                                                                "/v3/api-docs/**", "/v3/api-docs.yaml",
                                                                "/swagger-resources/**", "/webjars/**", "/api-docs/**")
                                                .permitAll()

                                                // Reglas para /pais/**
                                                .requestMatchers(HttpMethod.GET, "/pais/**").authenticated()
                                                .requestMatchers(HttpMethod.POST, "/pais/**")
                                                .hasAuthority(Roles.ADMIN.name())
                                                .requestMatchers(HttpMethod.PUT, "/pais/**")
                                                .hasAuthority(Roles.ADMIN.name())
                                                .requestMatchers(HttpMethod.DELETE, "/pais/**")
                                                .hasAuthority(Roles.ADMIN.name())

                                                // Reglas para /cliente/** y /usuario/**
                                                .requestMatchers(HttpMethod.GET, "/cliente/**", "/usuario/**")
                                                .authenticated()
                                                .requestMatchers(HttpMethod.POST, "/cliente/**", "/usuario/**")
                                                .hasAnyAuthority(Roles.CLIENTE.name(), Roles.ARTISTA.name(),
                                                                Roles.ADMIN.name())
                                                .requestMatchers(HttpMethod.PUT, "/cliente/**", "/usuario/**")
                                                .hasAnyAuthority(Roles.CLIENTE.name(), Roles.ARTISTA.name(),
                                                                Roles.ADMIN.name())
                                                .requestMatchers(HttpMethod.DELETE, "/cliente/**", "/usuario/**")
                                                .hasAnyAuthority(Roles.CLIENTE.name(), Roles.ARTISTA.name(),
                                                                Roles.ADMIN.name())

                                                // Reglas existentes para /artista/**, /albums/**, etc.
                                                .requestMatchers(HttpMethod.GET,
                                                                "/artista/**",
                                                                "/albums/**",
                                                                "/cancion/**",
                                                                "/categoria/**",
                                                                "/colaboracion/**",
                                                                "/search/**")
                                                .authenticated()
                                                .requestMatchers(HttpMethod.POST,
                                                                "/artista/**",
                                                                "/albums/**",
                                                                "/cancion/**",
                                                                "/categoria/**",
                                                                "/colaboracion/**")
                                                .hasAuthority(Roles.ARTISTA.name())
                                                .requestMatchers(HttpMethod.PUT,
                                                                "/artista/**",
                                                                "/albums/**",
                                                                "/cancion/**",
                                                                "/categoria/**",
                                                                "/colaboracion/**")
                                                .hasAuthority(Roles.ARTISTA.name())
                                                .requestMatchers(HttpMethod.DELETE,
                                                                "/artista/**",
                                                                "/albums/**",
                                                                "/cancion/**",
                                                                "/categoria/**",
                                                                "/colaboracion/**")
                                                .hasAuthority(Roles.ARTISTA.name())
                                                .anyRequest().hasAuthority(Roles.ADMIN.name())) // Catch-all para ADMIN
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