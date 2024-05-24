package com.core.linkup.membership.company.entity;

import com.core.linkup.common.entity.BaseEntity;
import com.core.linkup.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity(name = "member_company_membership")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MemberCompanyMembership extends BaseEntity {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer personnelCount;

    @ManyToOne
    private Member member;

    @ManyToOne
    private CompanyMembership companyMembership;
}
