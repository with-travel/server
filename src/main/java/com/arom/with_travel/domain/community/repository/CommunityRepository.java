package com.arom.with_travel.domain.community.repository;

import com.arom.with_travel.domain.community.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;

public interface CommunityRepository extends JpaRepository<Community, Long>,
        JpaSpecificationExecutor<Community> {

    @EntityGraph(attributePaths = {"member", "images"})
    @Query("select c from Community c where c.id = :id")
    Community findDetailById(Long id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Community c set c.viewCount = c.viewCount + 1 where c.id = :id")
    int increaseViewCount(Long id);

    default Page<Community> search(Specification<Community> spec, Pageable pageable) {
        return this.findAll(spec, pageable);
    }
}
