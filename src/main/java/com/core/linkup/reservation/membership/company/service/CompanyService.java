package com.core.linkup.reservation.membership.company.service;

import com.core.linkup.reservation.membership.company.converter.CompanyConverter;
import com.core.linkup.reservation.membership.company.entity.Company;
import com.core.linkup.reservation.membership.company.entity.CompanyMembership;
import com.core.linkup.reservation.membership.company.repository.CompanyRepository;
import com.core.linkup.reservation.membership.company.request.CompanyRequest;
import com.core.linkup.reservation.membership.company.response.CompanyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyConverter companyConverter;

    // 반환값
    public Company buildCompany(CompanyRequest request) {
        return Company.builder()
                .name(request.getName())
                .managerPhone(request.getManagerPhone())
                .managerEmail(request.getManagerEmail())
                .consentContact(request.isConsentContact())
                .consentPromotion(request.isConsentPromotion())
                .companyMemberships(new ArrayList<>())
                .build();
    }

    @Transactional
    public Company saveCompany(Company company, CompanyMembership companyMembership){
        company.getCompanyMemberships().add(companyMembership);
        return companyRepository.save(company);
    }

    public CompanyResponse toResponse(Company company){
        return companyConverter.toCompanyResponse(company);
    }

}
