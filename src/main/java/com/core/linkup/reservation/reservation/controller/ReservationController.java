package com.core.linkup.reservation.reservation.controller;

import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.reservation.reservation.response.MainPageReservationResponse;
import com.core.linkup.reservation.reservation.response.MembershipReservationResponse;
import com.core.linkup.reservation.reservation.response.MembershipResponse;
import com.core.linkup.reservation.reservation.response.SeatSpaceResponse;
import com.core.linkup.reservation.reservation.service.MembershipReservationService;
import com.core.linkup.reservation.reservation.service.ReservationService;
import com.core.linkup.security.MemberDetails;
import com.querydsl.core.Tuple;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/reservation")
@Tag(name = "Reservation", description = "예약 관련 API")
public class ReservationController {

    private final MembershipReservationService membershipReservationService;
    private final ReservationService reservationService;

    // 사용자 전체 멤버십 목록 조회 // TODO: null 일 때 처리
    @GetMapping("/my-membership")
    public BaseResponse<List<MembershipResponse>> getMyMemberships(
            @AuthenticationPrincipal MemberDetails memberDetails){
        return BaseResponse.response(
                membershipReservationService.getAllMyMemberships(memberDetails.getMember()));
    }

    // 잔여 좌석 조회
    @GetMapping("/{officeId}")
    public BaseResponse<List<SeatSpaceResponse>> getAvailableSeats(
            @PathVariable Long officeId,
            @RequestParam(name = "type") String type,
            @RequestParam(name = "start") String start,
            @RequestParam(name = "end") String end){

        return BaseResponse.response(
                reservationService.getAvailableSeatSpaces(officeId, type, start, end)); // 오픈데스크
    }

    @GetMapping // (뒤에 yyyy-mm-dd)
    public BaseResponse<List<MainPageReservationResponse>> getMainPageReservations(
            @AuthenticationPrincipal MemberDetails memberDetails,
            @RequestParam(name = "date", required = false) LocalDate date) {

        if (date == null) {
            date = LocalDate.now();
        }
        return BaseResponse.response(
                membershipReservationService.getAllReservationsOnDate(memberDetails.getMember(), date));
    }

    @GetMapping("/recent")
    public BaseResponse<MembershipReservationResponse> getMostRecent(
            @AuthenticationPrincipal MemberDetails memberDetails
    ){
        MembershipReservationResponse mostRecent = reservationService.findMostRecent(memberDetails);

        return BaseResponse.response(mostRecent);
    }
}
