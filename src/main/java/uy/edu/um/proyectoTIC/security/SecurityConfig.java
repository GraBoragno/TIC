package uy.edu.um.proyectoTIC.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Desactiva la protección CSRF (solo para pruebas)
                .authorizeRequests()
                .anyRequest().permitAll() // Permitir cualquier URL
                .and()
                .httpBasic(); // Permite autenticación básica (opcional)

        return http.build();
    }
    }

