package com.db.manga.config.security.objsql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("objsql")
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        return new CustomUserDetailsManager(customUserDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        // Set up the DaoAuthenticationProvider with CustomUserDetailsService
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // Dostęp tylko dla ADMIN
                        .requestMatchers("/user/**").hasAnyRole("CREATOR", "ADMIN")  // Dostęp dla USER i ADMIN
                        .requestMatchers("/reader/**").hasAnyRole("READER", "CREATOR", "ADMIN")
                        .requestMatchers("/public/**").permitAll()      // Publiczny dostęp
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/register").permitAll()
                        .anyRequest().authenticated()                   // Wymagane logowanie dla pozostałych endpointów
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")         // Ścieżka do wylogowania
                        .logoutSuccessUrl("/")        // Przekierowanie po wylogowaniu
                )
                .csrf(csrf -> csrf.disable());    // Wyłącz CSRF (opcjonalnie, np. dla REST API)

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
