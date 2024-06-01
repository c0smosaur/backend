package com.core.linkup.reservation.membership.company.service;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.common.utils.AuthCodeUtils;
import com.core.linkup.common.utils.EmailUtils;
import com.core.linkup.common.utils.RedisUtils;
import com.core.linkup.reservation.membership.company.converter.CompanyMembershipConverter;
import com.core.linkup.reservation.membership.company.entity.Company;
import com.core.linkup.reservation.membership.company.entity.CompanyMembership;
import com.core.linkup.reservation.membership.company.repository.CompanyMembershipRepository;
import com.core.linkup.reservation.membership.company.repository.CompanyRepository;
import com.core.linkup.reservation.membership.company.request.CompanyMembershipRequest;
import com.core.linkup.reservation.membership.company.request.CompanyRequest;
import com.core.linkup.reservation.reservation.converter.ReservationConverter;
import com.core.linkup.reservation.reservation.request.CompanyMembershipRegistrationRequest;
import com.core.linkup.reservation.reservation.response.CompanyMembershipRegistrationResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyMembershipService {

    private final CompanyRepository companyRepository;
    private final CompanyMembershipRepository companyMembershipRepository;

    private final CompanyMembershipConverter companyMembershipConverter;
    private final ReservationConverter reservationConverter;

    private final AuthCodeUtils authCodeUtils;
    private final RedisUtils redisUtils;
    private final EmailUtils emailUtils;

    // 기업 생성, 기업 멤버십 생성
    @Transactional
    public CompanyMembershipRegistrationResponse registerCompanyMembership(CompanyMembershipRegistrationRequest request) {
        Company company = saveCompany(request.getCompany());
        CompanyMembership companyMembership =
                saveCompanyMembership(request.getCompanyMembership(), company.getId());

        String authCode = authCodeUtils.createCompanyAuthCode();
        sendCompanyAuthCode(company, authCode);
        redisUtils.saveCompanyAuthCode(authCode, String.valueOf(company.getId()));

        return reservationConverter.toCompanyMembershipRegistrationResponse(
                companyMembershipConverter.toCompanyResponse(company),
                companyMembershipConverter.toMembershipResponse(companyMembership)
        );
    }

    // 기업 멤버십 인증코드 전송
    public void sendCompanyAuthCode(Company company, String authCode) {
        String subject = "LinkUp 기업 멤버십 인증번호";

        try {
            emailUtils.sendEmail(company.getManagerEmail(), subject, authCode);
        } catch (Exception e) {
            log.error("messaging error");
            throw new BaseException(BaseResponseStatus.EMAIL_ERROR);
        }
    }

    // 기업 멤버십 생성
    public CompanyMembership saveCompanyMembership(CompanyMembershipRequest request,
                                                   Long companyId) {

        return companyMembershipRepository.save(
                companyMembershipConverter.toCompanyMembership(
                        request, companyId));
    }

    public Company saveCompany(CompanyRequest request) {
        return companyRepository.save(
                companyMembershipConverter.toCompanyEntity(request));
    }
}
