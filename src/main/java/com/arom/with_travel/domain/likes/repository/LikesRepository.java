package com.arom.with_travel.domain.likes.repository;

import com.arom.with_travel.domain.accompanies.model.Accompany;
import com.arom.with_travel.domain.likes.Likes;
import com.arom.with_travel.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    // TODO : 연관관계 엔티티가 아닌 필드로 조회하도록 변경 예정
    Optional<Likes> findByAccompanyAndMember(Accompany accompany, Member member);
}
