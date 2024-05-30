package com.core.linkup.reservation.membership.individual.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class IndividualMembershipResponse {

    private Long id;
    private String location;
    private Long price;
    private String type;
    private Integer duration;
    private LocalDate startDate;
    private LocalDate endDate;

    private Long memberId;
}
