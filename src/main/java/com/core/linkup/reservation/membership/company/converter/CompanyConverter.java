package com.core.linkup.reservation.membership.company.converter;

import com.core.linkup.common.annotation.Converter;
import com.core.linkup.reservation.membership.company.entity.Company;
import com.core.linkup.reservation.membership.company.response.CompanyResponse;

@Converter
public class CompanyConverter {

    public CompanyResponse toCompanyResponse(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .managerPhone(company.getManagerPhone())
                .managerEmail(company.getManagerEmail())
                .build();
    }
}
