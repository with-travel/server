package com.arom.with_travel.domain.accompanies.service;

import com.arom.with_travel.domain.accompanies.model.Accompany;
import com.arom.with_travel.domain.accompanies.model.AccompanyApply;
import com.arom.with_travel.domain.accompanies.dto.request.AccompaniesPostRequest;
import com.arom.with_travel.domain.accompanies.dto.response.AccompaniesDetailsResponse;
import com.arom.with_travel.domain.accompanies.repository.accompany.AccompaniesRepository;
import com.arom.with_travel.domain.accompanies.repository.accompanyApply.AccompanyApplyRepository;
import com.arom.with_travel.domain.likes.Likes;
import com.arom.with_travel.domain.likes.repository.LikesRepository;
import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccompanyService {

    private final AccompaniesRepository accompaniesRepository;
    private final MemberRepository memberRepository;
    private final LikesRepository likesRepository;
    private final AccompanyApplyRepository applyRepository;

    @Transactional
    public String save(AccompaniesPostRequest request, Long memberId) {
        Member member = findMember(memberId);
        Accompany accompany = Accompany.from(request);
        accompany.post(member);
        accompaniesRepository.save(accompany);
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
    public AccompaniesDetailsResponse showDetails(Long accompanyId){
        Accompany accompany = findAccompany(accompanyId);
        accompany.addView();
        return AccompaniesDetailsResponse.from(accompany);
    }

    // TODO : 동행 선택 알고리즘 구현
//    @Transactional(readOnly = true)
//    public List<AccompaniesBriefResponse> showBrief(){
//        List<AccompaniesBriefResponse> briefResponses = new ArrayList<>();
//
//    }

    @Transactional
    public String applyAccompany(Long accompanyId, Long memberId){
        Accompany accompany = findAccompany(accompanyId);
        Member member = findMember(memberId);
        isAlreadyApplied(member, accompany);
        applyRepository.save(AccompanyApply.apply(accompany, member));
        // TODO : 동행 작성자에게 참가 수락여부 알림이 가야 함
        return "참가 신청이 완료됐습니다.";
    }

//    @Transactional
//    public String confirmApply(Long accompanyId, Long writerId, Long applierId){
//
//    }

    // 임시 조회 코드
    private Member findMember(Long memberId){
        return memberRepository.findById(memberId)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

    private Accompany findAccompany(Long accompanyId){
        return accompaniesRepository.findById(accompanyId)
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
