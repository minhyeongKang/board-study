package com.hellomeen.boardstudy.global.config;

import com.hellomeen.boardstudy.global.jwt.JwtUtil;
import com.hellomeen.boardstudy.global.jwt.TokenRepository;
import com.hellomeen.boardstudy.global.security.JwtAuthenticationFilter;
import com.hellomeen.boardstudy.global.security.JwtAuthorizationFilter;
import com.hellomeen.boardstudy.global.security.JwtLogoutHandler;
import com.hellomeen.boardstudy.global.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends SecurityConfigurerAdapter {

    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration configuration;

    @Bean
    public JwtLogoutHandler jwtLogoutHandler() {
        return new JwtLogoutHandler(tokenRepository, jwtUtil);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil, tokenRepository);
        filter.setAuthenticationManager(authenticationManager(configuration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthentizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, tokenRepository, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf((csrf) -> csrf.disable());

        httpSecurity.sessionManagement(
                (sessionManagemnet) -> sessionManagemnet
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.authorizeHttpRequests(
                (authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers("/error").permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/api/v1/users/**").permitAll()
                        .anyRequest().authenticated());

        httpSecurity
                .addFilterBefore(jwtAuthentizationFilter(), JwtAuthenticationFilter.class);
        httpSecurity
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
}
