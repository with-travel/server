package com.arom.with_travel.domain.member.repository;

import com.arom.with_travel.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email); // 카카오 ID로 회원 조회
    Optional<Member> findById(Long id);
}
