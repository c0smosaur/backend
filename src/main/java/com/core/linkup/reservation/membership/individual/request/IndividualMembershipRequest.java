package com.core.linkup.reservation.membership.individual.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class IndividualMembershipRequest {

    private String location;
    private String type;
    private Integer duration;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long price;
}
