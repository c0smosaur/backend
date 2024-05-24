package com.core.linkup.reservation.membership.company.service;

import com.core.linkup.reservation.membership.company.converter.CompanyConverter;
import com.core.linkup.reservation.membership.company.entity.Company;
import com.core.linkup.reservation.membership.company.repository.CompanyRepository;
import com.core.linkup.reservation.membership.company.request.CompanyRequest;
import com.core.linkup.reservation.membership.company.response.CompanyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyConverter companyConverter;

    // 반환값
    public Company saveCompany(CompanyRequest request) {
        Company company = Company.builder()
                .name(request.getName())
                .managerPhone(request.getManagerPhone())
                .managerEmail(request.getManagerEmail())
                .consentContact(request.isConsentContact())
                .consentPromotion(request.isConsentPromotion())
                .build();

        return companyRepository.save(company);
    }

    public CompanyResponse toResponse(Company company){
        return companyConverter.toCompanyResponse(company);
    }

}
