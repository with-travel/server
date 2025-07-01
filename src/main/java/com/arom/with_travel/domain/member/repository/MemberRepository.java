package com.arom.with_travel.domain.member.repository;

import com.arom.with_travel.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String Email);
    Optional<Member> findById(Long memberId);
    boolean existsByEmail(String email);
    boolean existsByOauthId(String oauthId);
}
