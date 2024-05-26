package com.core.linkup.reservation.membership.individual.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class IndividualMembershipResponse {

    private Long id;
    private String location;
    private Long price;
    private String type;
    private Integer duration;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Long memberId;
}
