package com.core.linkup.reservation.membership.company.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class CompanyMembershipResponse {

    private Long id;
    private String location;
    private Long price;
    private Integer duration;
    private Integer credit;
    private Integer staffCount;
    private LocalDate startDate;
    private LocalDate endDate;

    private Long companyId;
}
