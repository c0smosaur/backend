package com.core.linkup.reservation.membership.individual.entity;

import com.core.linkup.common.entity.BaseEntity;
import com.core.linkup.reservation.membership.individual.entity.enums.MembershipType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "individual_membership")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class IndividualMembership extends BaseEntity {

    private String location;
    private Long price;
    @Enumerated(EnumType.STRING)
    private MembershipType type;
    private Integer duration;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
