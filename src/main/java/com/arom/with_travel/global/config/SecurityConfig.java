package com.arom.with_travel.global.config;

import com.arom.with_travel.domain.member.service.MemberService;
import com.arom.with_travel.domain.member.service.MemberSignupService;
import com.arom.with_travel.global.jwt.filter.TokenAuthenticationFilter;
import com.arom.with_travel.global.jwt.repository.RefreshTokenRepository;
import com.arom.with_travel.global.jwt.service.TokenProvider;
import com.arom.with_travel.global.oauth2.handler.OAuth2SuccessHandler;
import com.arom.with_travel.global.oauth2.service.CustomOAuth2UserService;
import com.arom.with_travel.global.oauth2.util.OAuth2AuthorizationRequestBasedOnCookieRepository;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberSignupService memberSignupService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                //
                .sessionManagement(management ->
                        management.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )

                //
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/signup").authenticated() //
                        .anyRequest().permitAll()
                )

                .cors(cors -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.addAllowedOrigin("http://localhost:8080");
                    configuration.addAllowedMethod("*");
                    configuration.addAllowedHeader("*");
                    configuration.setAllowCredentials(true);
                    configuration.addAllowedOriginPattern("*");
                    cors.configurationSource(request -> configuration);
                })

                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)

                // OAuth2 로그인 성공 시
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authorizationEndpoint ->
                                authorizationEndpoint.authorizationRequestRepository(
                                        oAuth2AuthorizationRequestBasedOnCookieRepository()
                                )
                        )
                        .userInfoEndpoint(userInfoEndpoint ->
                                userInfoEndpoint.userService(customOAuth2UserService)
                        )
                        .successHandler(oAuth2SuccessHandler(memberSignupService)) // GUEST or USER 분기
                )

                // 예외 처리  API 경로 → 401 반환
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .defaultAuthenticationEntryPointFor(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                                new AntPathRequestMatcher("/api/**")
                        )
                )

                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler(MemberSignupService memberSignupService) {
        return new OAuth2SuccessHandler(
                tokenProvider,
                refreshTokenRepository,
                oAuth2AuthorizationRequestBasedOnCookieRepository(),
                memberSignupService
        );
    }

    @Bean
    public Filter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider);
    }

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
