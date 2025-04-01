package com.arom.with_travel.domain.accompanies.repository.accompany;

import com.arom.with_travel.domain.accompanies.model.Accompany;
import com.arom.with_travel.domain.accompanies.model.Continent;
import com.arom.with_travel.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccompanyRepository extends
        JpaRepository<Accompany, Long>,
        AccompaniesRepositoryCustom {

    @Query("SELECT a FROM Accompany a WHERE a.continent = :continent")
    Page<Accompany> findByContinent(@Param("continent") Continent continent, Pageable pageable);

    List<Accompany> findAccompaniesByMember(Member member);
}
