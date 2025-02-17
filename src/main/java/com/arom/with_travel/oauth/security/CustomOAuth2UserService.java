package com.arom.with_travel.oauth.security;


import com.arom.with_travel.oauth.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
/*
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo userInfo = switch (provider) {
            case "naver" -> new NaverUserInfo(oAuth2User.getAttributes());
            case "kakao" -> new KakaoUserInfo(oAuth2User.getAttributes());
            default -> throw new IllegalArgumentException("Unsupported provider");
        };

        User user = userRepository.findByProviderAndProviderId(provider, userInfo.getId())
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .provider(provider)
                            .providerId(userInfo.getId())
                            .name(userInfo.getName())
                            .email(userInfo.getEmail())
                            .build();
                    return userRepository.save(newUser);
                });

        return new DefaultOAuth2User(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")),
                oAuth2User.getAttributes(), "id");

 */
    }
}