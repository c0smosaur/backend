package com.core.linkup.reservation.reservation.service;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.common.utils.AuthCodeUtils;
import com.core.linkup.common.utils.EmailUtils;
import com.core.linkup.common.utils.RedisUtils;
import com.core.linkup.reservation.membership.company.entity.Company;
import com.core.linkup.reservation.membership.company.entity.CompanyMembership;
import com.core.linkup.reservation.membership.company.response.CompanyMembershipResponse;
import com.core.linkup.reservation.membership.company.service.CompanyMembershipService;
import com.core.linkup.reservation.membership.company.service.CompanyService;
import com.core.linkup.reservation.reservation.request.CompanyRegistrationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final CompanyService companyService;
    private final CompanyMembershipService companyMembershipService;
    private final AuthCodeUtils authCodeUtils;
    private final EmailUtils emailUtils;
    private final RedisUtils redisUtils;

    public CompanyMembershipResponse registerCompanyMembership(CompanyRegistrationRequest request) {
        Company company = companyService.saveCompany(request.getCompany());
        CompanyMembership companyMembership =
                companyMembershipService.saveCompanyMembership(request.getCompanyMembership(), company);

        sendCompanyAuthCode(company);

        return companyMembershipService.toResponse(companyMembership);
    }

    public void sendCompanyAuthCode(Company company){
        String subject = "LinkUp 기업 멤버십 인증번호";
        String authCode = authCodeUtils.createCompanyAuthCode();
        try {
            emailUtils.sendEmail(company.getManagerEmail(), subject, authCode);
            redisUtils.saveAuthCode(company.getManagerEmail(), authCode);
        } catch (Exception e) {
            log.error("messaging error");
            throw new BaseException(BaseResponseStatus.EMAIL_ERROR);
        }
    }


}
