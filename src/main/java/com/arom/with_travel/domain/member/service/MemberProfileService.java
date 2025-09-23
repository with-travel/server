package com.arom.with_travel.domain.member.service;

import com.arom.with_travel.domain.accompanies.dto.response.AccompanyDetailsResponse;
import com.arom.with_travel.domain.accompanies.model.Accompany;
import com.arom.with_travel.domain.accompanies.model.AccompanyApply;
import com.arom.with_travel.domain.accompanies.repository.accompany.AccompanyApplyRepository;
import com.arom.with_travel.domain.accompanies.repository.accompany.AccompanyRepository;
import com.arom.with_travel.domain.image.Image;
import com.arom.with_travel.domain.image.ImageType;
import com.arom.with_travel.domain.image.repository.ImageRepository;
import com.arom.with_travel.domain.likes.repository.LikesRepository;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.dto.response.MemberProfileResponseDto;
import com.arom.with_travel.domain.member.error.MemberException;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import com.arom.with_travel.global.oauth2.dto.CustomOAuth2User;
import com.arom.with_travel.global.security.domain.AuthenticatedMember;
import com.arom.with_travel.global.security.domain.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.arom.with_travel.domain.member.error.MemberErrorCode.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberProfileService {

    private final MemberRepository memberRepository;
    private final AccompanyRepository accompanyRepository;
    private final AccompanyApplyRepository applyRepository;
    private final LikesRepository likesRepository;
    private final ImageRepository imageRepository;

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

    public void uploadMemberProfileImage(String email, String imageName, String imageUrl){
        Member member = loadMemberOrThrow(email);
        Image image = Image.fromMember(imageName, imageUrl, member, ImageType.MEMBER_PROFILE);
        imageRepository.save(image);
    }

    private Member loadMemberOrThrow(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> MemberException.from(MEMBER_NOT_FOUND)
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
