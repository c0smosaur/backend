package com.core.linkup.reservation.reservation.controller;

import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.reservation.reservation.request.CompanyMembershipRegistrationRequest;
import com.core.linkup.reservation.reservation.request.IndividualMembershipRegistrationRequest;
import com.core.linkup.reservation.reservation.response.CompanyMembershipRegistrationResponse;
import com.core.linkup.reservation.reservation.response.IndividualMembershipReservationListResponse;
import com.core.linkup.reservation.reservation.response.ReservationResponse;
import com.core.linkup.reservation.reservation.service.CompanyMembershipReservationService;
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
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final CompanyMembershipReservationService companyMembershipReservationService;
    private final IndividualMembershipReservationService individualMembershipReservationService;

    // 기업 등록, 기업 멤버십 생성, 기업 인증번호 발송
    @PostMapping("/company")
    public BaseResponse<CompanyMembershipRegistrationResponse> registerCompany(
            @RequestBody CompanyMembershipRegistrationRequest request) {
        CompanyMembershipRegistrationResponse response =
                companyMembershipReservationService.registerCompanyMembership(request);
        return BaseResponse.response(response);
    }

    // 해당 지점의 개인 멤버십+예약(좌석, 공간) 예약 생성
    @PostMapping("/{officeId}")
    public BaseResponse<IndividualMembershipReservationListResponse> registerIndividual(
            @RequestBody IndividualMembershipRegistrationRequest request,
            @PathVariable Long officeId, @AuthenticationPrincipal MemberDetails memberDetails){
        IndividualMembershipReservationListResponse response =
                individualMembershipReservationService.registerIndividualMembership(request, memberDetails.getMember(), officeId);
        return BaseResponse.response(response);
    }

    // 사용자의 전체 개인 멤버십과 전체 예약 조회
    @GetMapping("/my-reservation")
    public BaseResponse<List<IndividualMembershipReservationListResponse>> getMyReservations(
            @AuthenticationPrincipal MemberDetails memberDetails) {

        return BaseResponse.response(
                individualMembershipReservationService.viewAllIndividualMembershipAndReservations(memberDetails.getMember()));
    }

    // 사용자의 특정 개인멤버십에 등록된 예약 전체 조회
//    @GetMapping("/my-reservation/{membershipId}")
//    public BaseResponse<List<ReservationResponse>> getMembershipReservations(@PathVariable Long membershipId){
//
//    }

    // 개인 멤버십 예약 개별 조회 api/v1/reservation/my-reservations/{membership_id}/{reservation_id}
//    @GetMapping("/my-reservations/{membershipId}/{reservationId}")
//    public BaseResponse<ReservationResponse> getReservation(@AuthenticationPrincipal MemberDetails memberDetails,
//                                                            @PathVariable Long membershipId,
//                                                            @PathVariable Long reservationId) {
//
//    }

    // 사용자 멤버십에 예약 추가(좌석, 공간)
    // 예약 추가 후 조회 쿼리 재활용
//    @PostMapping("/{officeId}/lounge")



    // 사용자 예약 수정 api/v1/reservation/my-reservations/{membership_id}/{reservation_id}



    // 사용자 예약 취소 api/v1/reservation/my-reservations/{membership_id}/{reservation_id}
// @DeleteMapping("/)

    // 남은 좌석 조회
    //@GetMapping("{office_id}/seat?type={string}&start={string}&end={string}")
//     public BaseResponse<List<SeatSpaceResponse>> getAvailableSeats(@RequestParam(name = "type") String type,
//                                                                    @RequestParam(name = "start") String start,
//                                                                    @RequestParam(name = "end") String end){
//
//    }


    //
}
