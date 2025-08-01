package com.arom.with_travel.domain.member.dto;

import com.arom.with_travel.domain.image.Image;
import com.arom.with_travel.domain.member.Member;

public record MemberProfileResponseDto(
//        boolean isPrinciple,
        String memberNickname,
        Member.TravelType travelType,
        Image image,
        String introduction
        //동행 데이터 추가
){
     public static MemberProfileResponseDto from(Member member){
        return new MemberProfileResponseDto(
//                isPrinciple,
                member.getNickname(),
                member.getTravelType(),
                member.getImage(),
                member.getIntroduction()
        );
    }
}
