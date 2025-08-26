package com.arom.with_travel.domain.community.service;

import com.arom.with_travel.domain.community.Community;
import com.arom.with_travel.domain.community.dto.CommunityCreateRequest;
import com.arom.with_travel.domain.community.dto.CommunityDetailResponse;
import com.arom.with_travel.domain.community.dto.CommunityListItemResponse;
import com.arom.with_travel.domain.community.dto.CommunityUpdateRequest;
import com.arom.with_travel.domain.community.repository.CommunityRepository;
import com.arom.with_travel.domain.image.Image;
import com.arom.with_travel.domain.image.repository.ImageRepository;
import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import com.arom.with_travel.global.exception.BaseException;
import com.arom.with_travel.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.arom.with_travel.domain.community.CommunitySpecs.*;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public Long create(String currentMemberEmail, CommunityCreateRequest req) {
        Member writer = memberRepository.findByEmail(currentMemberEmail)
                .orElseThrow(() -> BaseException.from(ErrorCode.MEMBER_NOT_FOUND));

        Community saved = communityRepository.save(
                Community.create(writer, req.getTitle(), req.getContent(),
                        req.getContinent(), req.getCountry(), req.getCity())
        );

        if (req.getImages() != null) {
            List<Image> newImages = req.getImages().stream()
                    .map(img -> Image.fromCommunity(
                            defaultName(img.getImageName()),
                            requireUrl(img.getImageUrl()),
                            saved
                    ))
                    .toList();
            if (!newImages.isEmpty()) imageRepository.saveAll(newImages);
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
            throw BaseException.from(ErrorCode.IMG_URL_MUST_FILLED);
        }
        return url;
    }

    @Transactional
    public CommunityDetailResponse readAndIncreaseView(Long id) {
        communityRepository.increaseViewCount(id);
        Community c = communityRepository.findDetailById(id);
        if (c == null) throw BaseException.from(ErrorCode.COMMUNITY_NOT_FOUND);
        List<String> urls = c.getImages().stream().map(Image::getImageUrl).collect(Collectors.toList());
        return new CommunityDetailResponse(
                c.getId(), c.getTitle(), c.getContent(),
                c.getContinent(), c.getCountry(), c.getCity(),
                c.getMember().getId(), c.getMember().getNickname(),
                c.getViewCount(), urls,
                c.getCreatedAt().toString(), c.getUpdatedAt().toString()
        );
    }

    @Transactional
    public void update(Long currentMemberId, Long communityId, CommunityUpdateRequest req) {
        Community c = communityRepository.findById(communityId)
                .orElseThrow(() -> BaseException.from(ErrorCode.COMMUNITY_NOT_FOUND));
        validateOwnership(currentMemberId, c.getMember().getId());

        c.update(req.getTitle(), req.getContent(), req.getContinent(), req.getCountry(), req.getCity());

        if (req.getImages() != null) {
            c.getImages().forEach(Image::detachFromCommunity);

            List<Image> newImages = req.getImages().stream()
                    .map(img -> Image.fromCommunity(
                            defaultName(img.getImageName()),
                            requireUrl(img.getImageUrl()),
                            c
                    ))
                    .toList();

            if (!newImages.isEmpty()) imageRepository.saveAll(newImages);
        }
    }

    @Transactional
    public void delete(Long currentMemberId, Long communityId) {
        Community c = communityRepository.findById(communityId)
                .orElseThrow(() -> BaseException.from(ErrorCode.COMMUNITY_NOT_FOUND));
        validateOwnership(currentMemberId, c.getMember().getId());
        communityRepository.delete(c);
    }

    @Transactional
    public Page<CommunityListItemResponse> search(String continent, String country, String city, String q,
                                                  Pageable pageable) {
        Specification<Community> spec = Specification
                .where(continentEq(continent))
                .and(countryEq(country))
                .and(cityEq(city))
                .and(keywordLike(q));

        return communityRepository.search(spec, pageable)
                .map(c -> new CommunityListItemResponse(
                        c.getId(),
                        c.getTitle(),
                        c.getContent().length() > 30 ? c.getContent().substring(0, 30) + "..." : c.getContent(),
                        c.getContinent(), c.getCountry(), c.getCity(),
                        c.getMember().getId(),
                        c.getMember().getNickname(),
                        c.getViewCount(),
                        c.getCreatedAt().toString()
                ));
    }

    private void validateOwnership(Long currentMemberId, Long ownerId) {
        if (!ownerId.equals(currentMemberId)) {
            throw BaseException.from(ErrorCode.COMMUNITY_FORBIDDEN);
        }
    }
}