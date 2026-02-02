/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.cafe.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 *
 * @author tu
 */
@Configuration
public class CorsConfig {

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http, CorsConfigurationSource cors) throws Exception{
        
        http
            .csrf(csrf -> csrf.disable())
            .cors(corsSpec -> corsSpec.configurationSource(cors))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").permitAll()
                .anyRequest().permitAll()
            );
        return http.build();
    }

}
