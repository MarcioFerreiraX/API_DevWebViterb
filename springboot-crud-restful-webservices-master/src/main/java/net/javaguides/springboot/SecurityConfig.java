package net.javaguides.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/eventos/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/atividades/**").permitAll()
                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                .requestMatchers("/api/organizador/**").hasRole("ORGANIZADOR")
                                .requestMatchers("/api/eventos/**").hasRole("ADMIN")
                                .requestMatchers("/api/edicoes/**").hasAnyRole("ADMIN", "ORGANIZADOR")
                                .requestMatchers("/api/espacos/**").hasRole("ORGANIZADOR")
                                .requestMatchers("/api/atividades/**").authenticated()
                                .requestMatchers(HttpMethod.POST, "/api/atividades/**").hasRole("ORGANIZADOR")
                                .requestMatchers(HttpMethod.PUT, "/api/atividades/**").hasRole("ORGANIZADOR")
                                .requestMatchers(HttpMethod.DELETE, "/api/atividades/**").hasRole("ORGANIZADOR")
                                .requestMatchers(HttpMethod.POST, "/api/atividades/{id}/favoritar").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/api/atividades/{id}/desfavoritar").authenticated()
                                .anyRequest().authenticated()
                )
                .httpBasic();
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }
}
