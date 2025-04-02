package com.arom.with_travel.domain.accompanies.service;

import com.arom.with_travel.domain.accompanies.dto.event.AccompanyAppliedEvent;
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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public String save(AccompanyPostRequest request, Long memberId) {
        Member member = loadMemberOrThrow(memberId);
        Accompany accompany = Accompany.from(request);
        accompany.post(member);
        accompanyRepository.save(accompany);
        return "등록 되었습니다";
    }

    @Transactional
    public boolean pressLike(Long accompanyId, Long memberId){
        Member member = loadMemberOrThrow(memberId);
        Accompany accompany = loadAccompanyOrThrow(accompanyId);
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
        Accompany accompany = loadAccompanyOrThrow(accompanyId);
        accompany.addView();
        return AccompanyDetailsResponse.from(accompany);
    }

    @Transactional
    public String applyAccompany(Long accompanyId, Long memberId){
        Accompany accompany = loadAccompanyOrThrow(accompanyId);
        Member proposer = loadMemberOrThrow(memberId);
        proposer.validateNotAlreadyAppliedTo(accompany);
        applyRepository.save(AccompanyApply.apply(accompany, proposer));
        AccompanyAppliedEvent event = new AccompanyAppliedEvent(
                accompany.getId(),
                accompany.getOwnerId(),
                proposer.getId(),
                proposer.getNickname()
        );
        eventPublisher.publishEvent(event);
        return "참가 신청이 완료됐습니다.";
    }

    @Transactional(readOnly = true)
    public Slice<AccompanyBriefResponse> getAccompaniesBrief(Country country, int size, Long lastId){
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Slice<Accompany> accompanyList = accompanyRepository.findByCountry(country, lastId, pageable);
        return accompanyList.map(AccompanyBriefResponse::from);
    }

    // 임시 조회 코드
    private Member loadMemberOrThrow(Long memberId){
        return memberRepository.findById(memberId)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

    private Accompany loadAccompanyOrThrow(Long accompanyId){
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

    //동행 정보 다 가져옴.
    public List<Accompany>[] getAccompanyByMember(Member member){
        //전체 리스트 가져옴
        LocalDate now = LocalDate.now();
        List<Accompany> accompanies = accompanyRepository.findAccompaniesByMember(member);
        List<AccompanyApply> accompanyApplies = applyRepository.findAccompanyAppliesByMember(member);

        //0은 모집한 동행 1은 신청한 동행 2는 이미 지난 동행
        List<Accompany>[] accompaniesList = new List[3];
        for(int i=0;i<3;i++) accompaniesList[i] = new ArrayList<>();

        for(Accompany accompany : accompanies){
            if(isFinish(accompany)) accompaniesList[2].add(accompany);
            else accompaniesList[0].add(accompany);
        }

        for(AccompanyApply accompanyApply : accompanyApplies){
            if(isFinish(accompanyApply.getAccompanies())) continue;
            accompaniesList[1].add(accompanyApply.getAccompanies());
        }

        return accompaniesList;
    }

    private boolean isFinish(Accompany accompany){
        LocalDate endDate = accompany.getEndDate();
        LocalDate now = LocalDate.now();

        // true면 지난 동행임
        if(now.compareTo(endDate)>0) return true;
        return false;
    }

}












