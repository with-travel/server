package com.arom.with_travel.domain.policy;

import com.arom.with_travel.domain.policy_agreement.PolicyAgreement;
import com.arom.with_travel.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Policy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private String policyTitle;
    @NotNull @Lob private String policyDescription;
    @NotNull private Boolean isRequired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_agreement_id")
    private PolicyAgreement policyAgreement;
}
