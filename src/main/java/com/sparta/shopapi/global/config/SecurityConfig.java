package com.sparta.shopapi.global.config;

import com.sparta.shopapi.global.jwt.JwtUil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final JwtUil jwtUil;

    public SecurityConfig(JwtUil jwtUil) {
        this.jwtUil = jwtUil;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/api/member/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/product/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/product/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )

                .csrf(auth->auth.disable())
                .formLogin(formLogin->formLogin.disable())
                .sessionManagement(auth->auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(auth->auth.disable())
                .build();
    }
}
