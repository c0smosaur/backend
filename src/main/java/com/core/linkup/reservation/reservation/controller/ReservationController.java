package com.core.linkup.reservation.reservation.controller;

import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.reservation.reservation.request.CompanyMembershipRegistrationRequest;
import com.core.linkup.reservation.reservation.request.IndividualMembershipRegistrationRequest;
import com.core.linkup.reservation.reservation.response.CompanyMembershipRegistrationResponse;
import com.core.linkup.reservation.reservation.response.IndividualMembershipRegistrationResponse;
import com.core.linkup.reservation.reservation.service.TotalReservationService;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final TotalReservationService totalReservationService;

    @PostMapping("/company")
    public BaseResponse<CompanyMembershipRegistrationResponse> registerCompany(@RequestBody CompanyMembershipRegistrationRequest request) {
        CompanyMembershipRegistrationResponse response = totalReservationService.registerCompanyMembership(request);
        return BaseResponse.response(response);
    }

    @PostMapping("/{officeId}")
    public BaseResponse<IndividualMembershipRegistrationResponse> registerIndividual(
            @RequestBody IndividualMembershipRegistrationRequest request,
            @PathVariable Long officeId, @AuthenticationPrincipal MemberDetails memberDetails){
        IndividualMembershipRegistrationResponse response =
                totalReservationService.registerIndividualMembership(request, memberDetails.getMember());
        return BaseResponse.response(response);
    }

}
