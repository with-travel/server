package com.arom.with_travel.domain.member.service;

import com.arom.with_travel.domain.accompanies.repository.accompany.AccompanyRepository;
import com.arom.with_travel.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SuppressWarnings("NonAsciiCharacters")
public class MemberProfileTest {
    @InjectMocks
    private MemberProfileService memberProfileService;

    @Mock
    private  MemberRepository memberRepository;
    @Mock
    private  AccompanyRepository accompanyRepository;


}
