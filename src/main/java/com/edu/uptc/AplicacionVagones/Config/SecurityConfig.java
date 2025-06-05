package com.edu.uptc.AplicacionVagones.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.edu.uptc.AplicacionVagones.Service.UsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UsuarioService usuarioService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.POST,"/usuarios/**").hasRole("ADMINISTRADOR")
            .requestMatchers("/vagoneta/**").hasAnyRole("TRABAJADOR","ADMINISTRADOR")
            .requestMatchers(HttpMethod.POST,"/vagoneta/**").hasAuthority("ROLE_ADMINISTRADOR")
            
            .requestMatchers(HttpMethod.POST,"/vagoneta/**").hasRole("ADMINISTRADOR")
            .requestMatchers(HttpMethod.GET,"/vagoneta/**").hasAnyRole( "TRABAJADOR","ADMINISTRADOR")
            .anyRequest().authenticated()
        )
        .formLogin(Customizer.withDefaults())
        .httpBasic(Customizer.withDefaults())
        .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
