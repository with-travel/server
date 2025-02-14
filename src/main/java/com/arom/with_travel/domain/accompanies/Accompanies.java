package com.arom.with_travel.domain.accompanies;

import com.arom.with_travel.domain.accompanies.dto.request.AccompaniesPostRequest;
import com.arom.with_travel.domain.accompanyReviews.AccompanyReviews;
import com.arom.with_travel.domain.image.Image;
import com.arom.with_travel.domain.likes.Likes;
import com.arom.with_travel.domain.member.Member;
import com.arom.with_travel.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Table(name = "accompanies",
//        indexes = {
//                @Index(name = "idx_accompanies_country", columnList = "country"),
//                @Index(name = "idx_accompanies_created_at", columnList = "created_at")
//        })
@SQLDelete(sql = "UPDATE member SET is_deleted = true, deleted_at = now() where id = ?")
@SQLRestriction("is_deleted is FALSE")
public class Accompanies extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private int recruitmentCount;

    @NotNull
    private String destination;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AccompanyType accompanyType;

    @NotNull
    private String accompanyTitle;

    @NotNull
    private String accompanyDescription;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private Long views = 0L;

    @Column
    @Enumerated(EnumType.STRING)
    private Continent continent;

    @Column
    @Enumerated(EnumType.STRING)
    private Country country;

    @Column
    @Enumerated(EnumType.STRING)
    private City city;

    @AllArgsConstructor
    public enum Continent{
        ASIA("AS")
        ;
        private final String continentCode;
    }

    @AllArgsConstructor
    public enum Country{
        // asia
        JAPAN("JPN")
        ;
        private final String countryCode;
    }

    @AllArgsConstructor
    public enum City{

        TOKYO("TOK"),
        OSAKA("OSA"),
        KYOTO("KYO")
        ;

        private final String cityCode;
    }

    @AllArgsConstructor
    public enum AccompanyType{
        LUNCH,
        DINNER,
        TOTAL
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "accompanies")
    private List<AccompanyReviews> accompanyReviews = new ArrayList<>();

    @OneToMany(mappedBy = "accompanies")
    private List<AccompanyApply> accompanyApplies = new ArrayList<>();

    @OneToMany(mappedBy = "accompanies")
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "accompanies")
    private List<Likes> likes = new ArrayList<>();

    @Builder
    public Accompanies(LocalTime startTime,
                       LocalDate startDate,
                       LocalDate endDate,
                       String accompanyDescription,
                       String accompanyTitle,
                       AccompanyType accompanyType,
                       String destination,
                       int recruitmentCount,
                       Continent continent,
                       Country country,
                       City city) {
        this.startTime = startTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.accompanyTitle = accompanyTitle;
        this.accompanyDescription= accompanyDescription;
        this.accompanyType = accompanyType;
        this.destination = destination;
        this.recruitmentCount = recruitmentCount;
        this.continent = continent;
        this.country = country;
        this.city = city;
    }

    public void post(Member member){
        this.member = member;
        member.getAccompanies().add(this);
    }

    public boolean isAlreadyLikedBy(Long memberId){
        return likes.stream()
                .anyMatch(like -> like.getMember().getId().equals(memberId));
    }

    public void addView(){
        views++;
    }

    private Long showViews(){
        return views;
    }

    public int showLikes(){
        return likes.size();
    }

    public static Accompanies from(AccompaniesPostRequest request){
        return Accompanies.builder()
                .startTime(request.getStartTime())
                .startDate(request.getStartDate())
                .endDate(request.getEndTime())
                .accompanyTitle(request.getTitle())
                .accompanyDescription(request.getDescription())
                .recruitmentCount(request.getRegisterCount())
                .destination(request.getDestination())
                .accompanyType(request.getAccompanyType())
                .continent(request.getContinentName())
                .country(request.getCountryName())
                .city(request.getCityName())
                .build();
    }
}
