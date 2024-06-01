package com.core.linkup.reservation.reservation.controller;

import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.reservation.reservation.request.IndividualMembershipRegistrationRequest;
import com.core.linkup.reservation.reservation.request.ReservationRequest;
import com.core.linkup.reservation.reservation.response.*;
import com.core.linkup.reservation.reservation.service.IndividualMembershipReservationService;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation/individual")
public class IndividualMembershipReservationController {

    private final IndividualMembershipReservationService individualMembershipReservationService;

    // 해당 지점의 개인 멤버십+예약(좌석, 공간) 예약 생성
    @PostMapping("/{officeId}")
    public BaseResponse<MembershipReservationListResponse> registerIndividual(
            @RequestBody IndividualMembershipRegistrationRequest request,
            @PathVariable Long officeId, @AuthenticationPrincipal MemberDetails memberDetails){
        MembershipReservationListResponse response =
                individualMembershipReservationService.registerIndividualMembership(
                        request, memberDetails.getMember(), officeId);
        return BaseResponse.response(response);
    }

    // 사용자의 전체 멤버십과 전체 예약 조회
//    @GetMapping("/my-reservation")
//    public BaseResponse<List<MembershipReservationListResponse>> getMyReservations(
//            @AuthenticationPrincipal MemberDetails memberDetails) {
//
//        return BaseResponse.response(
//                membershipReservationService.getAllMyMembershipsAndReservations(memberDetails.getMember()));
//    }

    // 사용자의 특정 개인멤버십에 등록된 예약 전체 조회
    @GetMapping("/my-membership/{membershipId}")
    public BaseResponse<List<ReservationResponse>> getMembershipReservations(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable Long membershipId){

        return BaseResponse.response(
                individualMembershipReservationService.getReservationsForIndividualMembership(
                        memberDetails.getMember(), membershipId));
    }

    // 개인 멤버십 예약 개별 조회
    @GetMapping("/my-membership/{membershipId}/reservation/{reservationId}")
    public BaseResponse<ReservationResponse> getReservation(@AuthenticationPrincipal MemberDetails memberDetails,
                                                            @PathVariable Long membershipId,
                                                            @PathVariable Long reservationId) {
        return BaseResponse.response(
                individualMembershipReservationService.getReservationForIndividualMembership(
                        memberDetails.getMember(), membershipId, reservationId));
    }

    // 사용자 멤버십에 예약 추가(좌석, 공간)
    // 예약 추가 후 조회 쿼리 재활용
    @PostMapping("/my-membership/{membershipId}")
    public BaseResponse<MembershipReservationListResponse> addReservation(@PathVariable Long membershipId,
                                                            @AuthenticationPrincipal MemberDetails memberDetails,
                                                            @RequestBody List<ReservationRequest> requests
                                                            ){
        return BaseResponse.response(
                individualMembershipReservationService.addIndividualReservations(
                        memberDetails.getMember(), requests, membershipId));
    }

    // 사용자 예약 수정
    @PutMapping("/my-membership/{membershipId}/reservation/{reservationId}")
    public BaseResponse<ReservationResponse> updateReservation(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @PathVariable Long membershipId,
            @PathVariable Long reservationId,
            @RequestBody ReservationRequest request){

        return BaseResponse.response(
                individualMembershipReservationService.updateDesignatedReservation(
                        request,reservationId, membershipId));
    }


    // 사용자 예약 삭제
    @DeleteMapping("/my-membership/{membershipId}/reservation/{reservationId}")
    public BaseResponse<Void> deleteReservation(@PathVariable Long membershipId,
                                            @PathVariable Long reservationId,
                                            @AuthenticationPrincipal MemberDetails memberDetails){
        return individualMembershipReservationService.deleteReservationForIndividualMembership(
                memberDetails.getMember(),membershipId, reservationId) ?
                BaseResponse.response(BaseResponseStatus.DELETE_SUCCESS) :
                BaseResponse.response(BaseResponseStatus.INVALID_REQUEST);
    }

}
