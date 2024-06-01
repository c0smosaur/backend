package com.core.linkup.reservation.reservation.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class MembershipResponse {
    private Long id;
    private String location;
    private Long price;
    private String type;
    private Integer duration;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer credit;
    private Integer staffCount;
    private Long memberId;
    private Long companyId;

}
