package com.core.linkup.reservation.membership.individual.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class IndividualMembershipRequest {

    private String location ;
    private String type;
    private Integer duration;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long price;
}
