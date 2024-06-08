package com.core.linkup.common.entity;

import com.core.linkup.reservation.membership.individual.entity.enums.MembershipType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseMembershipEntity extends BaseEntity{

    private Long officeId;
    private String location;
    private Long price;
    private Integer duration;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private MembershipType type;
}
