package com.core.linkup.reservation.membership.company.converter;

import com.core.linkup.common.annotation.Converter;
import com.core.linkup.reservation.membership.company.entity.CompanyMembership;
import com.core.linkup.reservation.membership.company.response.CompanyMembershipResponse;

@Converter
public class CompanyMembershipConverter {

    public CompanyMembershipResponse toCompanyMembershipResponse(CompanyMembership companyMembership) {
        return CompanyMembershipResponse.builder()
                .id(companyMembership.getId())
                .location(companyMembership.getLocation())
                .duration(companyMembership.getDuration())
                .price(companyMembership.getPrice())
                .credit(companyMembership.getCredit())
                .staffCount(companyMembership.getStaffCount())
                .startDate(companyMembership.getStartDate())
                .endDate(companyMembership.getEndDate())
                .companyId(companyMembership.getCompany().getId())
                .build();
    }
}
