package com.arom.with_travel.domain.accompanies;

import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.domain.shorts.Shorts;
import com.arom.with_travel.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET is_deleted = true, deleted_at = now() where id = ?")
@SQLRestriction("is_deleted is FALSE")
public class Accompanies extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull private int recruitmentCount;
    @NotNull private String destination;
    @NotNull private String continent;
    @NotNull private String country;
    @NotNull private String city;
    @NotNull private LocalDate date;
    @NotNull private LocalTime time;

    @OneToMany(mappedBy = "accompanies")
    private List<Accompanies> accompanyReviews = new ArrayList<>();
}
