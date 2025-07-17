package com.alibou.whatsappclone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/v3/api-docs",
                                        "/swagger-resources/**",
                                        "/swagger-resources",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/webjars/**",
                                        "/swagger-ui.html",
                                        "/ws/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .oauth2ResourceServer(auth ->
                        auth.jwt(token  -> token.jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter())));

        return http.build();
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin();
    }
}
