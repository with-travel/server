package com.arom.with_travel.domain.accompanies.service;

import com.arom.with_travel.domain.accompanies.dto.response.AccompanyBriefResponse;
import com.arom.with_travel.domain.accompanies.dto.response.CursorSliceResponse;
import com.arom.with_travel.domain.accompanies.model.Accompany;
import com.arom.with_travel.domain.accompanies.dto.request.AccompanyPostRequest;
import com.arom.with_travel.domain.accompanies.dto.response.AccompanyDetailsResponse;
import com.arom.with_travel.domain.accompanies.model.Country;
import com.arom.with_travel.domain.accompanies.repository.accompany.AccompanyRepository;
import com.arom.with_travel.domain.image.Image;
import com.arom.with_travel.domain.image.ImageType;
import com.arom.with_travel.domain.image.repository.ImageRepository;
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

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccompanyService {

    private final AccompanyRepository accompanyRepository;
    private final MemberRepository memberRepository;
    private final LikesRepository likesRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public String createAccompany(AccompanyPostRequest request, String oauthId) {
        Member member = loadMemberOrThrow(oauthId);
        Accompany accompany = Accompany.from(request);
        accompany.post(member);
        List<Image> images = request.getImages()
                .stream()
                .map(imageReq -> Image.fromAccompany(imageReq.getImageName(), imageReq.getImageUrl(), accompany, ImageType.ACCOMPANY))
                .toList();
        imageRepository.saveAll(images);
        accompanyRepository.save(accompany);
        return "등록 되었습니다";
    }

    @Transactional
    public boolean toggleLike(Long accompanyId, String oauthId){
        Member member = loadMemberOrThrow(oauthId);
        Accompany accompany = loadAccompanyOrThrow(accompanyId);
        Optional<Likes> like = loadLikes(accompany, member);
        if (like.isPresent()) {
            likesRepository.delete(like.get());
            return false;
        }
        Likes newLike = Likes.create(member, accompany);
        likesRepository.save(newLike);
        return true;
    }

    @Transactional
    public AccompanyDetailsResponse showAccompanyDetails(Long accompanyId){
        Accompany accompany = loadAccompanyOrThrow(accompanyId);
        accompany.addView();
        long likesCount = likesRepository.countByAccompanyId(accompanyId);
        return AccompanyDetailsResponse.from(accompany, likesCount);
    }

    @Transactional(readOnly = true)
    public CursorSliceResponse<AccompanyBriefResponse> showAccompaniesBrief(Country country, int size, Long lastId){
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Slice<AccompanyBriefResponse> dtoSlice = accompanyRepository
                .findByCountry(country, lastId, pageable)
                .map(AccompanyBriefResponse::from);
        return CursorSliceResponse.of(dtoSlice);
    }

    private Member loadMemberOrThrow(String oauthId){
        return memberRepository.findByOauthId(oauthId)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));
    }

    private Accompany loadAccompanyOrThrow(Long accompanyId){
        return accompanyRepository.findById(accompanyId)
                .orElseThrow(() -> BaseException.from(ErrorCode.ACCOMPANY_NOT_FOUND));
    }

    private Optional<Likes> loadLikes(Accompany accompany, Member member) {
        return likesRepository.findByAccompanyAndMember(accompany, member);
    }
}












