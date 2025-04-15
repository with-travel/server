package com.arom.with_travel.domain.survey.repository;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    List<Survey> findByMember(Member member);
}
