package com.core.linkup.reservation.membership.company.response;

import com.core.linkup.reservation.membership.company.entity.Company;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class CompanyMembershipResponse {

    private Long id;
    private String location;
    private Long price;
    private Integer duration;
    private Integer credit;
    private Integer staffCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Company company;
}
