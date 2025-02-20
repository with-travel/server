package com.arom.with_travel.domain.accompanies.repository.accompany;

import com.arom.with_travel.domain.accompanies.model.Accompany;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccompaniesRepository extends
        JpaRepository<Accompany, Long>,
        AccompaniesRepositoryCustom {
}
