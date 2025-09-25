package com.arom.with_travel.global.config;

import com.arom.with_travel.global.security.config.CustomAccessDeniedHandler;
import com.arom.with_travel.global.security.config.CustomAuthenticationEntryPoint;
import com.arom.with_travel.global.security.filter.JwtFilter;
import com.arom.with_travel.global.security.filter.SecurityExceptionFilter;
import com.arom.with_travel.global.security.service.MemberDetailsService;
import com.arom.with_travel.global.security.token.provider.JwtProvider;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final MemberDetailsService memberDetailsService;
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/signup/**").permitAll()
                        .requestMatchers("/", "/index/**", "/index.js", "/favicon.ico",
                                "/templates", "/error", "/v3/api-docs/**", "/swagger-ui/**", "/api/v1/login",
                                "/actuator/**","/api/v1/signup/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .securityContext(securityContext -> {
                    securityContext
                            .requireExplicitSave(true);
                })
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)

                // 예외 처리  API 경로 → 401 반환
//                .exceptionHandling(exceptionHandling -> exceptionHandling
//                        .defaultAuthenticationEntryPointFor(
//                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
//                                new AntPathRequestMatcher("/api/**")
//                        )
//                )
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(securityExceptionFilter(), JwtFilter.class)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtProvider, memberDetailsService, securityContextRepository());
    }

    @Bean
    public SecurityExceptionFilter securityExceptionFilter() {
        return new SecurityExceptionFilter(handlerExceptionResolver);
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
