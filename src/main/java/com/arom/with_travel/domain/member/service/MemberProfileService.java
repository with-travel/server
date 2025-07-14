package com.arom.with_travel.domain.member.service;

import com.arom.with_travel.domain.accompanies.dto.response.AccompanyDetailsResponse;
import com.arom.with_travel.domain.accompanies.model.Accompany;
import com.arom.with_travel.domain.accompanies.model.AccompanyApply;
import com.arom.with_travel.domain.accompanies.repository.accompany.AccompanyApplyRepository;
import com.arom.with_travel.domain.accompanies.repository.accompany.AccompanyRepository;
import com.arom.with_travel.domain.accompanies.repository.accompany.AccompanyApplyRepository;
import com.arom.with_travel.domain.likes.repository.LikesRepository;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.dto.MemberProfileRequestDto;
import com.arom.with_travel.domain.member.dto.MemberProfileResponseDto;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberProfileService {

    private final MemberRepository memberRepository;
    private final AccompanyRepository accompanyRepository;
    private final AccompanyApplyRepository applyRepository;
    private final LikesRepository likesRepository;

    //내가 등록한 동행 정보들
    public List<AccompanyDetailsResponse> myPostAccompany(String email){
        Member member = loadMemberOrThrow(email);

        List<Accompany> accompanies = accompanyRepository.findAccompaniesByMember(member);
        if(accompanies==null || accompanies.isEmpty()) log.warn("동행 정보 확인 불가");

        return accompanies.stream()
                .filter(accompany -> !accompany.isFinish())
                .map(accompany -> {
                    long likes = likesRepository.countByAccompanyId(accompany.getId());
                    return AccompanyDetailsResponse.from(accompany, likes);
                })
                .collect(Collectors.toList());
    }

    //내가 신청한 동행 정보들
    public List<AccompanyDetailsResponse> myApplyAccompany(String email){
        Member member = loadMemberOrThrow(email);
        List<AccompanyApply> accompanyApplies = applyRepository.findAccompanyAppliesByMember(member);

        return accompanyApplies.stream()
                .filter(accompanyApply -> !accompanyApply.getAccompany().isFinish())
                .map(accompanyApply -> {
                    long likes = likesRepository.countByAccompanyId(accompanyApply.getId());
                    return AccompanyDetailsResponse.from(accompanyApply.getAccompany(),likes);
                })
                .collect(Collectors.toList());
    }

    //지난 동행 정보들
    public List<AccompanyDetailsResponse> myPastAccompany(String email){
        Member member = loadMemberOrThrow(email);
        List<Accompany> accompanies = accompanyRepository.findAccompaniesByMember(member);

        return accompanies.stream()
                .map(accompany -> {
                    long likes = likesRepository.countByAccompanyId(accompany.getId());
                    return AccompanyDetailsResponse.from(accompany,likes);
                })
                .collect(Collectors.toList());
    }


    private Member loadMemberOrThrow(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND)
        );
    }

    public MemberProfileResponseDto getProfile(String email){
        Member member= loadMemberOrThrow(email);
        return MemberProfileResponseDto.from(member);
    }

    public String getIntroduction(String email){
        Member member = loadMemberOrThrow(email);
        return member.getIntroduction();
    }
}
