package com.arom.with_travel.domain.community;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class CommunitySpecs {
    private CommunitySpecs() {}

    public static Specification<Community> continentEq(String continent) {
        return (root, q, cb) -> StringUtils.hasText(continent) ? cb.equal(root.get("continent"), continent) : null;
    }

    public static Specification<Community> countryEq(String country) {
        return (root, q, cb) -> StringUtils.hasText(country) ? cb.equal(root.get("country"), country) : null;
    }

    public static Specification<Community> cityEq(String city) {
        return (root, q, cb) -> StringUtils.hasText(city) ? cb.equal(root.get("city"), city) : null;
    }

    public static Specification<Community> keywordLike(String qword) {
        return (root, q, cb) -> {
            if (!StringUtils.hasText(qword)) return null;
            String like = "%" + qword.trim() + "%";
            return cb.or(
                    cb.like(root.get("title"), like),
                    cb.like(root.get("content"), like)
            );
        };
    }
}
