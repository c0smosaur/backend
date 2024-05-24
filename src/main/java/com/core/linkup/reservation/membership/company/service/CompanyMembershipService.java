package com.core.linkup.reservation.membership.company.service;

import com.core.linkup.reservation.membership.company.converter.CompanyMembershipConverter;
import com.core.linkup.reservation.membership.company.entity.Company;
import com.core.linkup.reservation.membership.company.entity.CompanyMembership;
import com.core.linkup.reservation.membership.company.repository.CompanyMembershipRepository;
import com.core.linkup.reservation.membership.company.request.CompanyMembershipRequest;
import com.core.linkup.reservation.membership.company.response.CompanyMembershipResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyMembershipService {

    private final CompanyMembershipRepository companyMembershipRepository;
    private final CompanyMembershipConverter companyMembershipConverter;

    public CompanyMembership saveCompanyMembership(CompanyMembershipRequest request,
                                                           Company company) {
        CompanyMembership companyMembership = CompanyMembership.builder()
                .location(request.getLocation())
                .price(request.getPrice())
                .duration(request.getDuration())
                .credit(0)
                .staffCount(request.getStaffCount())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .company(company)
                .build()
                ;

        return companyMembershipRepository.save(companyMembership);
    }

    public CompanyMembershipResponse toResponse(CompanyMembership companyMembership) {
        return companyMembershipConverter.toCompanyMembershipResponse(companyMembership);
    }

}
