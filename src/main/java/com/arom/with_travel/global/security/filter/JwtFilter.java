package com.arom.with_travel.global.security.filter;

import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.security.domain.PrincipalDetails;
import com.arom.with_travel.global.security.error.AuthException;
import com.arom.with_travel.global.security.service.MemberDetailsService;
import com.arom.with_travel.global.security.token.provider.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.arom.with_travel.global.security.error.AuthErrorCode.EMPTY_TOKEN_PROVIDED;
import static com.arom.with_travel.global.security.token.properties.JwtProperties.HEADER_AUTHORIZATION;
import static com.arom.with_travel.global.security.token.properties.JwtProperties.TOKEN_PREFIX;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final MemberDetailsService memberDetailsService;
    private final SecurityContextRepository securityContextRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwtHeader = request.getHeader(HEADER_AUTHORIZATION);
        if (jwtHeader == null || !jwtHeader.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = resolveToken(request);
        String email = jwtProvider.getEmailFromAccessToken(token);
        PrincipalDetails pd = memberDetailsService.loadUserByUsername(email);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                pd,
                null,
                pd.getAuthorities()
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest req) {
        String authorization = req.getHeader(HEADER_AUTHORIZATION);
        if (authorization == null) {
            throw AuthException.from(EMPTY_TOKEN_PROVIDED);
        }
        if (authorization.startsWith(TOKEN_PREFIX)) {
            return authorization.substring(7);
        }
        throw AuthException.from(EMPTY_TOKEN_PROVIDED);
    }
}
