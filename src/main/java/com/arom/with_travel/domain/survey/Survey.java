package com.arom.with_travel.domain.survey;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.survey.enums.EnergyLevel;
import com.arom.with_travel.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "energy_level", nullable = false, length = 50)
    private EnergyLevel energyLevel;

    public static Survey create(
            Member member,
            EnergyLevel energyLevel
//            TravelGoal travelGoal,
//            TravelPace travelPace,
//            CommStyle commStyle,
//            Personality personality,
//            CompanionStyle companionStyle,
//            SpendPattern spendPattern
    ) {
        Survey s = new Survey();
        s.member = member;
        s.energyLevel = energyLevel;
//        s.travelGoal = travelGoal;
//        s.travelPace = travelPace;
//        s.commStyle = commStyle;
//        s.personality = personality;
//        s.companionStyle = companionStyle;
//        s.spendPattern = spendPattern;
        return s;
    }

    public void update(
            EnergyLevel energyLevel
//            TravelGoal travelGoal,
//            TravelPace travelPace,
//            CommStyle commStyle,
//            Personality personality,
//            CompanionStyle companionStyle,
//            SpendPattern spendPattern
    ) {
        this.energyLevel = energyLevel;
//        this.travelGoal = travelGoal;
//        this.travelPace = travelPace;
//        this.commStyle = commStyle;
//        this.personality = personality;
//        this.companionStyle = companionStyle;
//        this.spendPattern = spendPattern;
    }
}
