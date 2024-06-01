package com.core.linkup.reservation.reservation.controller;

import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.reservation.reservation.request.CompanyMembershipRegistrationRequest;
import com.core.linkup.reservation.reservation.response.CompanyMembershipRegistrationResponse;
import com.core.linkup.reservation.reservation.service.CompanyMembershipReservationService;
import com.core.linkup.reservation.reservation.service.IndividualMembershipReservationService;
import com.core.linkup.reservation.reservation.service.MembershipReservationService;
import com.core.linkup.reservation.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class CompanyMembershipReservationController {

    private final CompanyMembershipReservationService companyMembershipReservationService;
    private final IndividualMembershipReservationService individualMembershipReservationService;
    private final MembershipReservationService membershipReservationService;
    private final ReservationService reservationService;

    // 기업 등록, 기업 멤버십 생성, 기업 인증번호 발송
    @PostMapping("/company")
    public BaseResponse<CompanyMembershipRegistrationResponse> registerCompany(
            @RequestBody CompanyMembershipRegistrationRequest request) {
        CompanyMembershipRegistrationResponse response =
                companyMembershipReservationService.registerCompanyMembership(request);
        return BaseResponse.response(response);
    }
}
