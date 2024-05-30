package com.core.linkup.reservation.membership.company.converter;

import com.core.linkup.common.annotation.Converter;
import com.core.linkup.reservation.membership.company.entity.Company;
import com.core.linkup.reservation.membership.company.entity.CompanyMembership;
import com.core.linkup.reservation.membership.company.response.CompanyMembershipResponse;
import com.core.linkup.reservation.membership.company.response.CompanyResponse;

@Converter
public class CompanyMembershipConverter {

    public CompanyResponse toCompanyResponse(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .managerPhone(company.getManagerPhone())
                .managerEmail(company.getManagerEmail())
                .build();
    }

    public CompanyMembershipResponse toCompanyMembershipResponse(CompanyMembership companyMembership) {
        return CompanyMembershipResponse.builder()
                .id(companyMembership.getId())
                .location(companyMembership.getLocation())
                .duration(companyMembership.getDuration())
                .price(companyMembership.getPrice())
                .credit(companyMembership.getCredit())
                .staffCount(companyMembership.getStaffCount())
                .startDate(companyMembership.getStartDate().toLocalDate())
                .endDate(companyMembership.getEndDate().toLocalDate())
                .companyId(companyMembership.getCompanyId())
                .build();
    }
}
