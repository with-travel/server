package com.arom.with_travel.domain.community.service;

import com.arom.with_travel.domain.community.Community;
import com.arom.with_travel.domain.community.CommunitySpecs;
import com.arom.with_travel.domain.community.dto.*;
import com.arom.with_travel.domain.community.enums.CommunityTag;
import com.arom.with_travel.domain.community.error.CommunityException;
import com.arom.with_travel.domain.community.repository.CommunityRepository;
import com.arom.with_travel.domain.image.Image;
import com.arom.with_travel.domain.image.error.ImageException;
import com.arom.with_travel.domain.image.repository.ImageRepository;
import com.arom.with_travel.domain.likes.Likes;
import com.arom.with_travel.domain.likes.repository.LikesRepository;
import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.error.MemberException;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.arom.with_travel.domain.community.CommunitySpecs.*;
import static com.arom.with_travel.domain.community.error.CommunityErrorCode.COMMUNITY_FORBIDDEN;
import static com.arom.with_travel.domain.community.error.CommunityErrorCode.COMMUNITY_NOT_FOUND;
import static com.arom.with_travel.domain.image.error.ImageErrorCode.IMG_SAVE_FAIL;
import static com.arom.with_travel.domain.image.error.ImageErrorCode.IMG_URL_MUST_FILLED;
import static com.arom.with_travel.domain.member.error.MemberErrorCode.MEMBER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;
    private final LikesRepository likesRepository;

    @Transactional
    public Long create(String currentMemberEmail, CommunityCreateRequest req) {
        Member writer = memberRepository.findByEmail(currentMemberEmail)
                .orElseThrow(() -> MemberException.from(MEMBER_NOT_FOUND));

        Community community = Community.create(
                writer,
                req.getTitle(),
                req.getContent(),
                req.getTag(),
                req.getContinent(),
                req.getCountry(),
                req.getCity()
        );

        Community saved = communityRepository.save(community);

        if (req.getImages() != null) {
            List<Image> newImages = req.getImages().stream()
                    .map(img -> Image.fromCommunity(
                            defaultName(img.getImageName()),
                            requireUrl(img.getImageUrl()),
                            saved
                    ))
                    .toList();
            try {
                if (!newImages.isEmpty()) {
                    imageRepository.saveAll(newImages);
                }
            } catch (Exception e) {
                throw ImageException.from(IMG_SAVE_FAIL);
            }
        }

        return saved.getId();
    }

    private static String defaultName(String name) {
        return (name == null || name.isBlank())
                ? java.util.UUID.randomUUID().toString()
                : name;
    }

    private static String requireUrl(String url) {
        if (url == null || url.isBlank()) {
            throw ImageException.from(IMG_URL_MUST_FILLED);
        }
        return url;
    }

    @Transactional
    public CommunityDetailResponse readAndIncreaseView(Long id) {
        if (!communityRepository.existsById(id)) {
            throw CommunityException.from(COMMUNITY_NOT_FOUND);
        }

        communityRepository.increaseViewCount(id);

        Community c = communityRepository.findDetailById(id);
        if (c == null) {
            throw CommunityException.from(COMMUNITY_NOT_FOUND);
        }

        List<String> urls = c.getImages().stream().map(Image::getImageUrl).toList();

        return new CommunityDetailResponse(
                c.getId(), c.getTitle(), c.getContent(), c.getTag(),
                c.getContinent(), c.getCountry(), c.getCity(),
                c.getMember().getId(), c.getMember().getNickname(),
                c.getViewCount(), c.getLikeCount(), c.getReplyCount(),
                urls, c.getCreatedAt().toString(), c.getUpdatedAt().toString()
        );
    }

    @Transactional
    public CommunityDetailResponse update(Long currentMemberId, Long communityId, CommunityUpdateRequest req) {
        Community c = communityRepository.findById(communityId)
                .orElseThrow(() -> CommunityException.from(COMMUNITY_NOT_FOUND));
        validateOwnership(currentMemberId, c.getMember().getId());

        c.update(
                req.getTitle(),
                req.getContent(),
                req.getTag(),
                req.getContinent(),
                req.getCountry(),
                req.getCity()
        );

        if (req.getImages() != null) {
            c.getImages().clear();

            List<Image> newImages = req.getImages().stream()
                    .map(imgReq -> Image.fromCommunity(
                            defaultName(imgReq.getImageName()),
                            requireUrl(imgReq.getImageUrl()),
                            c
                    ))
                    .toList();

            if (!newImages.isEmpty()) {
                imageRepository.saveAll(newImages);
            }
            c.getImages().addAll(newImages);
        }

        return CommunityDetailResponseMapper.from(c);
    }

    @Transactional
    public void delete(Long currentMemberId, Long communityId) {
        Community c = communityRepository.findById(communityId)
                .orElseThrow(() -> CommunityException.from(COMMUNITY_NOT_FOUND));
        validateOwnership(currentMemberId, c.getMember().getId());
        communityRepository.delete(c);
    }

    @Transactional(readOnly = true)
    public Page<CommunityListItemResponse> search(
            String continent,
            String country,
            String city,
            String q,
            CommunityTag tag,
            Pageable pageable
    ) {
        Specification<Community> spec = Specification
                .where(CommunitySpecs.tagEq(tag))
                .and(continentEq(continent))
                .and(countryEq(country))
                .and(cityEq(city))
                .and(keywordLike(q));

        return communityRepository.search(spec, pageable)
                .map(c -> new CommunityListItemResponse(
                        c.getId(),
                        c.getTitle(),
                        c.getContent().length() > 30 ? c.getContent().substring(0, 30) + "..." : c.getContent(),
                        c.getTag(),
                        c.getContinent(), c.getCountry(), c.getCity(),
                        c.getMember().getId(),
                        c.getMember().getNickname(),
                        c.getViewCount(),
                        c.getLikeCount(),
                        c.getReplyCount(),
                        c.getCreatedAt().toString()
                ));
    }

    @Transactional(readOnly = true)
    public Page<CommunityListItemResponse> listByTag(CommunityTag tag, Pageable pageable) {
        return communityRepository.findByTagOrderByCreatedAtDesc(tag, pageable)
                .map(c -> new CommunityListItemResponse(
                        c.getId(), c.getTitle(),
                        c.getContent().length() > 30 ? c.getContent().substring(0, 30) + "..." : c.getContent(),
                        c.getTag(), c.getContinent(), c.getCountry(), c.getCity(),
                        c.getMember().getId(), c.getMember().getNickname(),
                        c.getViewCount(), c.getLikeCount(), c.getReplyCount(),
                        c.getCreatedAt().toString()
                ));
    }

    @Transactional(readOnly = true)
    public List<CommunityListItemResponse> topLiked() {
        return communityRepository.findTop2ByOrderByLikeCountDescIdDesc().stream()
                .map(c -> new CommunityListItemResponse(
                        c.getId(), c.getTitle(),
                        c.getContent().length() > 30 ? c.getContent().substring(0, 30) + "..." : c.getContent(),
                        c.getTag(), c.getContinent(), c.getCountry(), c.getCity(),
                        c.getMember().getId(), c.getMember().getNickname(),
                        c.getViewCount(), c.getLikeCount(), c.getReplyCount(),
                        c.getCreatedAt().toString()
                ))
                .toList();
    }

    @Transactional
    public boolean toggleLike(Long communityId, Long currentMemberId) {
        Community c = communityRepository.findById(communityId)
                .orElseThrow(() -> CommunityException.from(COMMUNITY_NOT_FOUND));
        Member m = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> MemberException.from(MEMBER_NOT_FOUND));

        return likesRepository.findByCommunityIdAndMemberId(communityId, currentMemberId)
                .map(existing -> {
                    likesRepository.delete(existing);
                    communityRepository.addLikeCount(communityId, -1);
                    return false;
                })
                .orElseGet(() -> {
                    likesRepository.save(Likes.forCommunity(m, c));
                    communityRepository.addLikeCount(communityId, +1);
                    return true;
                });
    }

    private void validateOwnership(Long currentMemberId, Long ownerId) {
        if (!ownerId.equals(currentMemberId)) {
            throw CommunityException.from(COMMUNITY_FORBIDDEN);
        }
    }
}
