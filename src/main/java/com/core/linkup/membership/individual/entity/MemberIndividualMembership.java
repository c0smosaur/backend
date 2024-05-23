package com.core.linkup.membership.individual.entity;

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

@Entity(name = "member_individual_membership")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MemberIndividualMembership extends BaseEntity {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ManyToOne
    private IndividualMembership individualMembership;

    @ManyToOne
    private Member member;
}
