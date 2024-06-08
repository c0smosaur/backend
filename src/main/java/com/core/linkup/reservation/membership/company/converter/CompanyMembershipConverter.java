package com.core.linkup.reservation.membership.company.converter;

import com.core.linkup.common.annotation.Converter;
import com.core.linkup.reservation.membership.company.entity.Company;
import com.core.linkup.reservation.membership.company.entity.CompanyMembership;
import com.core.linkup.reservation.membership.company.request.CompanyMembershipRequest;
import com.core.linkup.reservation.membership.company.request.CompanyRequest;
import com.core.linkup.reservation.membership.company.response.CompanyResponse;
import com.core.linkup.reservation.membership.individual.entity.enums.MembershipType;
import com.core.linkup.reservation.reservation.response.MembershipResponse;

@Converter
public class CompanyMembershipConverter {

    public Company toCompanyEntity(CompanyRequest request){
        return Company.builder()
                .name(request.getName())
                .managerPhone(request.getManagerPhone())
                .managerEmail(request.getManagerEmail())
                .consentContact(request.isConsentContact())
                .consentPromotion(request.isConsentPromotion())
                .build();
    }

    public CompanyResponse toCompanyResponse(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .managerPhone(company.getManagerPhone())
                .managerEmail(company.getManagerEmail())
                .build();
    }

    public CompanyMembership toCompanyMembership(Long officeId,
                                                 CompanyMembershipRequest request,
                                                 Long companyId){
        return CompanyMembership.builder()
                .location(request.getLocation())
                .officeId(officeId)
                .type(MembershipType.COMPANY_PASS)
                .price(request.getPrice())
                .duration(request.getDuration())
                .credit(0)
                .staffCount(request.getStaffCount())
                .startDate(request.getStartDate().atStartOfDay())
                .endDate(request.getEndDate().atStartOfDay())
                .companyId(companyId)
                .build();
    }

    public MembershipResponse toMembershipResponse(CompanyMembership companyMembership) {
        return MembershipResponse.builder()
                .id(companyMembership.getId())
                .officeId(companyMembership.getOfficeId())
                .type(companyMembership.getType().getName())
                .location(companyMembership.getLocation())
                .duration(companyMembership.getDuration())
                .price(companyMembership.getPrice())
                .startDate(companyMembership.getStartDate().toLocalDate())
                .endDate(companyMembership.getEndDate().toLocalDate())
                .companyId(companyMembership.getCompanyId())
                .credit(companyMembership.getCredit())
                .staffCount(companyMembership.getStaffCount())
                .build();
    }
}
