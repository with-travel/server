package com.arom.with_travel.domain.member.repository;

import com.arom.with_travel.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
