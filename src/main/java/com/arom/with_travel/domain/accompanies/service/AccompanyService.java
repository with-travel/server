package com.arom.with_travel.domain.accompanies.service;

import com.arom.with_travel.domain.accompanies.dto.response.AccompanyBriefResponse;
import com.arom.with_travel.domain.accompanies.model.Accompany;
import com.arom.with_travel.domain.accompanies.model.AccompanyApply;
import com.arom.with_travel.domain.accompanies.dto.request.AccompanyPostRequest;
import com.arom.with_travel.domain.accompanies.dto.response.AccompanyDetailsResponse;
import com.arom.with_travel.domain.accompanies.model.Continent;
import com.arom.with_travel.domain.accompanies.model.Country;
import com.arom.with_travel.domain.accompanies.repository.accompany.AccompanyRepository;
import com.arom.with_travel.domain.accompanies.repository.accompanyApply.AccompanyApplyRepository;
import com.arom.with_travel.domain.likes.Likes;
import com.arom.with_travel.domain.likes.repository.LikesRepository;
import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccompanyService {

    private final AccompanyRepository accompanyRepository;
    private final MemberRepository memberRepository;
    private final LikesRepository likesRepository;
    private final AccompanyApplyRepository applyRepository;

    @Transactional
    public String save(AccompanyPostRequest request, Long memberId) {
        Member member = findMember(memberId);
        Accompany accompany = Accompany.from(request);
        accompany.post(member);
        accompanyRepository.save(accompany);
        return "등록 되었습니다";
    }

    @Transactional
    public boolean pressLike(Long accompanyId, Long memberId){
        Member member = findMember(memberId);
        Accompany accompany = findAccompany(accompanyId);
        if(accompany.isAlreadyLikedBy(memberId)) {
            return false;
        }
        Likes like = Likes.init();
        like.update(member, accompany);
        likesRepository.save(like);
        return true;
    }

    @Transactional
    public AccompanyDetailsResponse showDetails(Long accompanyId){
        Accompany accompany = findAccompany(accompanyId);
        accompany.addView();
        return AccompanyDetailsResponse.from(accompany);
    }

    @Transactional
    public String applyAccompany(Long accompanyId, Long memberId){
        Accompany accompany = findAccompany(accompanyId);
        Member member = findMember(memberId);
        isAlreadyApplied(member, accompany);
        applyRepository.save(AccompanyApply.apply(accompany, member));
        // TODO : 동행 작성자에게 참가 수락여부 알림이 가야 함
        return "참가 신청이 완료됐습니다.";
    }

    @Transactional(readOnly = true)
    public Slice<AccompanyBriefResponse> getAccompaniesBrief(Country country, int size, Long lastId){
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Slice<Accompany> accompanyList = accompanyRepository.findByCountry(country, lastId, pageable);
        return accompanyList.map(AccompanyBriefResponse::from);
    }

    // 임시 조회 코드
    private Member findMember(Long memberId){
        return memberRepository.findById(memberId)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

    private Accompany findAccompany(Long accompanyId){
        return accompanyRepository.findById(accompanyId)
                .orElseThrow(() -> BaseException.from(ErrorCode.ACCOMPANY_NOT_FOUND));
    }

    private void isAlreadyApplied(Member member, Accompany accompany) {
        member.getAccompanyApplies()
                .stream()
                .map(accompanyApply ->{
                    if(accompanyApply.getAccompanies().equals(accompany))
                        throw BaseException.from(ErrorCode.TMP_ERROR);
                    return null;
                });
    }
}
