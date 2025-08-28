package com.arom.with_travel.domain.survey.repository;

import com.arom.with_travel.domain.survey.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    Optional<Survey> findByMemberIdAndIsDeletedFalse(Long memberId);
    boolean existsByMemberIdAndIsDeletedFalse(Long memberId);
}
