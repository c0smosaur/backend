package com.core.linkup.reservation.membership.individual.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MembershipType {

    ONE_DAY_PASS("1일 패스"),
    THIRTY_DAYS_PASS("30일 패스");

    private final String name;
}
