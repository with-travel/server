package com.arom.with_travel.domain.likes;

import com.arom.with_travel.domain.accompanies.model.Accompany;
import com.arom.with_travel.domain.member.Member;

public class LikeFixture {
    public static Likes createLikes(Member member, Accompany accompany){
        return Likes.create(member, accompany);
    }
}
