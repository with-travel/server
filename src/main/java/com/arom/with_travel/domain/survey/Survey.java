package com.arom.with_travel.domain.survey;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.survey.dto.request.SurveyRequestDto;
import com.arom.with_travel.domain.survey.enums.*;
import com.arom.with_travel.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE survey SET is_deleted = true, deleted_at = now() where id = ?")
@SQLRestriction("is_deleted is FALSE")
public class Survey extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "survey_energy_levels", joinColumns = @JoinColumn(name = "survey_id"))
    @Column(name = "energy_level", length = 50)
    @Enumerated(EnumType.STRING)
    private Set<EnergyLevel> energyLevels = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "survey_travel_goals", joinColumns = @JoinColumn(name = "survey_id"))
    @Column(name = "travel_goal", length = 50)
    @Enumerated(EnumType.STRING)
    private Set<TravelGoal> travelGoals = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "survey_travel_paces", joinColumns = @JoinColumn(name = "survey_id"))
    @Column(name = "travel_pace", length = 50)
    @Enumerated(EnumType.STRING)
    private Set<TravelPace> travelPaces = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "survey_comm_styles", joinColumns = @JoinColumn(name = "survey_id"))
    @Column(name = "comm_style", length = 50)
    @Enumerated(EnumType.STRING)
    private Set<CommStyle> commStyles = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "survey_personalities", joinColumns = @JoinColumn(name = "survey_id"))
    @Column(name = "personality", length = 50)
    @Enumerated(EnumType.STRING)
    private Set<RecordTendency> recordTendencies = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "survey_companion_styles", joinColumns = @JoinColumn(name = "survey_id"))
    @Column(name = "companion_style", length = 50)
    @Enumerated(EnumType.STRING)
    private Set<CompanionStyle> companionStyles = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "survey_spend_patterns", joinColumns = @JoinColumn(name = "survey_id"))
    @Column(name = "spend_pattern", length = 50)
    @Enumerated(EnumType.STRING)
    private Set<SpendPattern> spendPatterns = new HashSet<>();

    @Column(name = "introduction", length = 1000)
    private String introduction;

    // ---- 생성/수정 로직 ----
    private Survey(Member member,
                   Set<EnergyLevel> energyLevels,
                   Set<TravelGoal> travelGoals,
                   Set<TravelPace> travelPaces,
                   Set<CommStyle> commStyles,
                   Set<RecordTendency> recordTendencies,
                   Set<CompanionStyle> companionStyles,
                   Set<SpendPattern> spendPatterns,
                   String introduction) {
        this.energyLevels   = safe(energyLevels);
        this.travelGoals    = safe(travelGoals);
        this.travelPaces    = safe(travelPaces);
        this.commStyles     = safe(commStyles);
        this.recordTendencies  = safe(recordTendencies);
        this.companionStyles= safe(companionStyles);
        this.spendPatterns  = safe(spendPatterns);
        this.introduction = introduction;
        linkMember(member);
    }

    public static Survey create(Member member, SurveyRequestDto dto){
        return new Survey(
                member,
                dto.getEnergyLevels(), dto.getTravelGoals(), dto.getTravelPaces(),
                dto.getCommStyles(), dto.getRecordTendencies(), dto.getCompanionStyles(),
                dto.getSpendPatterns(), dto.getIntroduction()
        );
    }

    public void update(SurveyRequestDto dto){
        this.energyLevels    = safe(dto.getEnergyLevels());
        this.travelGoals     = safe(dto.getTravelGoals());
        this.travelPaces     = safe(dto.getTravelPaces());
        this.commStyles      = safe(dto.getCommStyles());
        this.recordTendencies   = safe(dto.getRecordTendencies());
        this.companionStyles = safe(dto.getCompanionStyles());
        this.spendPatterns   = safe(dto.getSpendPatterns());
        this.introduction = dto.getIntroduction();
    }

    private <T> Set<T> safe(Set<T> in){
        return (in == null) ? new HashSet<>() : new HashSet<>(in);
    }

    private void linkMember(Member newMember) {
        if (this.member != null && this.member.getSurvey() == this) {
            this.member.setSurvey(null);
        }
        this.member = newMember;
        if (newMember.getSurvey() != this) {
            newMember.setSurvey(this);
        }
    }
}
