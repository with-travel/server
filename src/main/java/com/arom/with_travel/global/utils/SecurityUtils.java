package com.arom.with_travel.global.utils;

import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.arom.with_travel.global.security.domain.AuthenticatedMember;
import com.arom.with_travel.global.security.domain.PrincipalDetails;
import com.arom.with_travel.global.security.error.AuthException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static com.arom.with_travel.global.security.error.AuthErrorCode.AUTH_UNSUPPORTED_PRINCIPAL;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static Long currentMemberIdOrThrow() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            throw new IllegalStateException("Unauthenticated");
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof PrincipalDetails pd) {
            return pd.getAuthenticatedMember().getMemberId();
        }
        if (principal instanceof AuthenticatedMember am) {
            return am.getMemberId();
        }

        throw AuthException.from(AUTH_UNSUPPORTED_PRINCIPAL);
    }
}
