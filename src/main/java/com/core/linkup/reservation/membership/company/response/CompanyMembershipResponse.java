package com.core.linkup.reservation.membership.company.response;

import com.core.linkup.reservation.membership.company.entity.Company;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class CompanyMembershipResponse {

    private Long id;
    private String location;
    private Long price;
    private Integer duration;
    private Integer credit;
    private Integer staffCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Long companyId;
}
