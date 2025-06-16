package com.arom.with_travel.domain.accompany;

import com.arom.with_travel.domain.accompanies.model.Accompany;
import com.arom.with_travel.domain.accompanies.model.AccompanyApply;
import com.arom.with_travel.domain.member.Member;

public class AccompanyApplyFixture {

    public static AccompanyApply createAccompanyApply(Accompany accompany, Member member) {
        return AccompanyApply.apply(accompany, member);
    }
}
