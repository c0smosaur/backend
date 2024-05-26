package com.core.linkup.reservation.reservation.controller;

import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.reservation.membership.company.service.CompanyService;
import com.core.linkup.reservation.reservation.request.CompanyMembershipRegistrationRequest;
import com.core.linkup.reservation.reservation.response.CompanyMembershipRegistrationResponse;
import com.core.linkup.reservation.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final CompanyService companyService;

    @PostMapping("/company")
    private BaseResponse<CompanyMembershipRegistrationResponse> registerCompany(@RequestBody CompanyMembershipRegistrationRequest request) {
        CompanyMembershipRegistrationResponse response = reservationService.registerCompanyMembership(request);
        return BaseResponse.response(response);
    }
}
