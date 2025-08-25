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

    private Survey(Member member, EnergyLevel energyLevel) {
        this.energyLevel = energyLevel;
        linkMember(member);
    }

    public static Survey create(Member member, EnergyLevel energyLevel) {
        return new Survey(member, energyLevel);
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

    public void update(EnergyLevel energyLevel) {
        this.energyLevel = energyLevel;
    }
}
