package com.core.linkup.reservation.membership.company.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CompanyMembershipRequest {
    private String location;
    private Long price;
    private Integer duration;
    private Integer staffCount;
    private LocalDate startDate;
    private LocalDate endDate;

}
