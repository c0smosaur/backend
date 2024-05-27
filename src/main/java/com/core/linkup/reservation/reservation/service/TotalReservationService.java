package com.core.linkup.reservation.reservation.service;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.common.utils.AuthCodeUtils;
import com.core.linkup.common.utils.EmailUtils;
import com.core.linkup.common.utils.RedisUtils;
import com.core.linkup.member.entity.Member;
import com.core.linkup.reservation.membership.company.entity.Company;
import com.core.linkup.reservation.membership.company.entity.CompanyMembership;
import com.core.linkup.reservation.membership.company.service.CompanyMembershipService;
import com.core.linkup.reservation.membership.company.service.CompanyService;
import com.core.linkup.reservation.membership.individual.entity.IndividualMembership;
import com.core.linkup.reservation.membership.individual.service.IndividualMembershipService;
import com.core.linkup.reservation.reservation.converter.ReservationConverter;
import com.core.linkup.reservation.reservation.entity.Reservation;
import com.core.linkup.reservation.reservation.request.CompanyMembershipRegistrationRequest;
import com.core.linkup.reservation.reservation.request.IndividualMembershipRegistrationRequest;
import com.core.linkup.reservation.reservation.request.ReservationRequest;
import com.core.linkup.reservation.reservation.response.CompanyMembershipRegistrationResponse;
import com.core.linkup.reservation.reservation.response.IndividualMembershipRegistrationResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TotalReservationService {

    private final CompanyService companyService;
    private final CompanyMembershipService companyMembershipService;
    private final IndividualMembershipService individualMembershipService;
    private final ReservationService reservationService;
    private final AuthCodeUtils authCodeUtils;
    private final EmailUtils emailUtils;
    private final RedisUtils redisUtils;
    private final ReservationConverter reservationConverter;

    @Transactional
    public CompanyMembershipRegistrationResponse registerCompanyMembership(CompanyMembershipRegistrationRequest request) {
        Company company = companyService.buildCompany(request.getCompany());
        CompanyMembership companyMembership =
                companyMembershipService.saveCompanyMembership(request.getCompanyMembership(), company);

        Company savedCompany = companyService.saveCompany(company, companyMembership);

        String authCode = authCodeUtils.createCompanyAuthCode();
        sendCompanyAuthCode(company, authCode);
        redisUtils.saveCompanyAuthCode(authCode, String.valueOf(company.getId())) ;

        return reservationConverter.toCompanyRegistrationResponse(
                companyService.toResponse(savedCompany),
                companyMembershipService.toResponse(companyMembership)
        );
    }

    public void sendCompanyAuthCode(Company company, String authCode){
        String subject = "LinkUp 기업 멤버십 인증번호";

        try {
            emailUtils.sendEmail(company.getManagerEmail(), subject, authCode);
            redisUtils.saveEmailAuthCode(company.getManagerEmail(), authCode);
        } catch (Exception e) {
            log.error("messaging error");
            throw new BaseException(BaseResponseStatus.EMAIL_ERROR);
        }
    }

    public IndividualMembershipRegistrationResponse registerIndividualMembership(
            IndividualMembershipRegistrationRequest requests,
            Member member) {

        IndividualMembership membership =
                individualMembershipService.buildIndividualMembership(requests.getMembership(), member);

        List<Reservation> reservations = new ArrayList<>();

        for (ReservationRequest request : requests.getReservations()){
            Reservation reservation = reservationService.buildReservation(request, membership);
            reservations.add(reservationService.saveReservation(reservation));
            membership.getReservations().add(reservation);
        }

        individualMembershipService.saveIndividualMembership(membership);

        return reservationConverter.toIndividualRegistrationResponse(
                individualMembershipService.toResponse(membership),
                reservations.stream().map(reservationService::toResponse).toList()
                );


    }
}
