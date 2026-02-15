package com.example.demo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())      // ✅ habilita CORS en Spring Security
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // ✅ importante para que el preflight CORS no sea bloqueado
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // (opcional) si quieres /api/hello público:
                .requestMatchers("/api/hello").permitAll()

                // el resto, protegido con Basic
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults()); // ✅ tu frontend usa Basic Auth

        // ❌ quita formLogin (para API + SPA normalmente no lo quieres)
        // .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(List.of(
            "https://pdauf-tema3-frontend.onrender.com",
            "http://localhost:5173" // ✅ por si pruebas Vite en local
        ));

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*")); // incluye Authorization
        config.setAllowCredentials(false);      // con Basic Auth no necesitas cookies

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
