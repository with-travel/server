package com.arom.with_travel.global.oauth2.service;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.global.oauth2.dto.CustomOAuth2User;
import com.arom.with_travel.global.oauth2.dto.KakaoResponse;
import com.arom.with_travel.global.oauth2.dto.OAuth2Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info(registrationId);
        OAuth2Response oAuth2Response = null;

        // 소셜로그인 인증 서버가 카카오인 경우.
        if (registrationId.equals("kakao")) {
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes().get("id").toString(),
                    oAuth2User.getAttributes());
        }


        Member loginUser = memberRepository.findByEmail(oAuth2Response.getEmail())
                .orElse(Member.create(oAuth2Response.getName(), oAuth2Response.getEmail(),
                        Member.Role.GUEST));

        return new CustomOAuth2User(oAuth2Response, Member.Role.GUEST, oAuth2Response.getOauthId());
    }
}