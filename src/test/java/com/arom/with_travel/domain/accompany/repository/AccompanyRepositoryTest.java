package com.arom.with_travel.domain.accompany.repository;

import com.arom.with_travel.domain.accompanies.service.AccompaniesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class AccompanyRepositoryTest {

    @Autowired
    AccompaniesService service;

    @Test
    @DisplayName("신규 동행 게시")
    void 새로운_동행_게시(){

    }

}
