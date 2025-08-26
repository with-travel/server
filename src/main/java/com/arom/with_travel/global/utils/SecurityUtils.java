package com.arom.with_travel.global.utils;

import com.arom.with_travel.global.security.domain.AuthenticatedMember;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static Long currentMemberIdOrThrow() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("Unauthenticated");
        }
        Object principal = auth.getPrincipal();

        if (principal instanceof AuthenticatedMember authenticatedMember) {
            return authenticatedMember.getMemberId();
        }

        throw new IllegalStateException("Unsupported principal: " + principal);
    }
}
