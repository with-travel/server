package com.arom.with_travel.domain.accompanies.repository.accompany;

import com.arom.with_travel.domain.accompanies.Accompanies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccompaniesRepository extends
        JpaRepository<Accompanies, Long>,
        AccompaniesRepositoryCustom {
}
