package com.arom.with_travel.domain.member.service;

import com.arom.with_travel.domain.accompanies.service.AccompanyService;
import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.dto.MemberSignupRequestDto;
import com.arom.with_travel.domain.member.dto.MemberSignupResponseDto;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.arom.with_travel.global.oauth2.dto.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MemberSignupService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    private Member findByIdOrThrow(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

    private Member findByEmailOrThrow(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

    private MemberSignupResponseDto toDto(Member m) {
        return MemberSignupResponseDto.from(m);
    }

    // 신규 회원 등록
    public Member createIfNotExists(CustomOAuth2User user) {
        return memberRepository
                .findByEmail(user.getEmail())
                .orElseGet(() -> {
                    Member inserted = Member.signUp(user.getEmail(), user.getOauthId());
                    return memberRepository.save(inserted);
                });
    }
  
private Member getUserByLoginEmailOrElseThrow(String loginEmail) {
        return memberRepository.findByEmail(loginEmail)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

    // 신규 회원 추가 정보 등록
    public MemberSignupResponseDto fillExtraInfo(String email,
                                                 MemberSignupRequestDto dto) {
        Member member = getUserByLoginEmailOrElseThrow(email);
        member.updateExtraInfo(dto.getNickname(),
                dto.getBirthdate(),
                dto.getGender());

        return MemberSignupResponseDto.from(member);
    }

    @Transactional(readOnly = true)
    public MemberSignupResponseDto getSignupInfo(String email) {
        Member member = getUserByLoginEmailOrElseThrow(email);
        return MemberSignupResponseDto.from(member);
    }
}
