package com.core.linkup.reservation.reservation.controller;

import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.reservation.reservation.request.CompanyMembershipRegistrationRequest;
import com.core.linkup.reservation.reservation.request.ReservationRequest;
import com.core.linkup.reservation.reservation.response.CompanyMembershipRegistrationResponse;
import com.core.linkup.reservation.reservation.response.MembershipReservationListResponse;
import com.core.linkup.reservation.reservation.response.ReservationResponse;
import com.core.linkup.reservation.reservation.service.CompanyMembershipReservationService;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/reservation/company")
@RequiredArgsConstructor
public class CompanyMembershipReservationController {

    private final CompanyMembershipReservationService companyMembershipReservationService;

    // 기업 등록, 기업 멤버십 생성, 기업 인증번호 발송
    @PostMapping("/{officeId}")
    public BaseResponse<CompanyMembershipRegistrationResponse> registerCompany(
            @RequestBody CompanyMembershipRegistrationRequest request,
            @PathVariable Long officeId
            ) {
        CompanyMembershipRegistrationResponse response =
                companyMembershipReservationService.registerCompanyMembership(officeId, request);
        return BaseResponse.response(response);
    }

    // 기업 멤버십 + 예약 목록 조회
    @GetMapping("/my-membership/{membershipId}")
    public BaseResponse<MembershipReservationListResponse> getMembershipReservations(
            @PathVariable Long membershipId,
            @AuthenticationPrincipal MemberDetails memberDetails){
        if (memberDetails.getMember().getCompanyMembershipId().equals(membershipId)){
            return BaseResponse.response(
                    companyMembershipReservationService.getReservationsForCompanyMembership(
                            memberDetails.getMember()));
        } else {
            return BaseResponse.response(BaseResponseStatus.INVALID_MEMBERSHIP_ID);
        }
    }

    // 기업 멤버십 예약 추가
    @PostMapping("/my-membership/{membershipId}")
    public BaseResponse<MembershipReservationListResponse> addReservation(
            @PathVariable Long membershipId,
            @RequestBody List<ReservationRequest> requests){
        return BaseResponse.response(
                companyMembershipReservationService.addCompanyReservations(
                        membershipId, requests));
    }

    // 기업 멤버십 예약 개별 조회
    @GetMapping("/my-membership/{membershipId}/reservation/{reservationId}")
    public BaseResponse<ReservationResponse> getReservation(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable Long membershipId,
            @PathVariable Long reservationId){
        return BaseResponse.response(
                companyMembershipReservationService.getReservationForCompanyMembership(
                memberDetails.getMember(), membershipId, reservationId));
    }

    // 기업 멤버십 예약 개별 수정
    @PutMapping("/my-membership/{membershipId}/reservation/{reservationId}")
    public BaseResponse<ReservationResponse> updateReservation(
            @PathVariable Long membershipId,
            @PathVariable Long reservationId,
            @RequestBody ReservationRequest request){
        return BaseResponse.response(
                companyMembershipReservationService.updateReservation(request, reservationId, membershipId));
    }

    // 기업 멤버십 예약 삭제
    @DeleteMapping("/my-membership/{membershipId}/reservation/{reservationId}")
    public BaseResponse<Void> deleteReservation(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable Long membershipId,
            @PathVariable Long reservationId){
        return companyMembershipReservationService.deleteReservationForCompanyMembership(
                memberDetails.getMember(), membershipId, reservationId) ?
                BaseResponse.response(BaseResponseStatus.DELETE_SUCCESS) :
                BaseResponse.response(BaseResponseStatus.INVALID_REQUEST);
    }
}
