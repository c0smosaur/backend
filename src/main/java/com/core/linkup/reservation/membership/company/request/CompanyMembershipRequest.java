package com.core.linkup.reservation.membership.company.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CompanyMembershipRequest {
    private String location;
    private Long price;
    private Integer duration;
    private Integer staffCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
