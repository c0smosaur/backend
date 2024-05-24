package com.core.linkup.reservation.reservation.controller;

import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.reservation.membership.company.response.CompanyMembershipResponse;
import com.core.linkup.reservation.reservation.request.CompanyRegistrationRequest;
import com.core.linkup.reservation.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/company")
    private BaseResponse<CompanyMembershipResponse> registerCompany(@RequestBody CompanyRegistrationRequest request) {
        CompanyMembershipResponse response = reservationService.registerCompanyMembership(request);
        return BaseResponse.response(response);
    }
}
