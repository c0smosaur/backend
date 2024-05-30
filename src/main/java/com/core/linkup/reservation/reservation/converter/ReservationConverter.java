package com.core.linkup.reservation.reservation.converter;

import com.core.linkup.common.annotation.Converter;
import com.core.linkup.office.entity.SeatSpace;
import com.core.linkup.reservation.membership.company.response.CompanyMembershipResponse;
import com.core.linkup.reservation.membership.company.response.CompanyResponse;
import com.core.linkup.reservation.membership.individual.response.IndividualMembershipResponse;
import com.core.linkup.reservation.reservation.entity.Reservation;
import com.core.linkup.reservation.reservation.response.CompanyMembershipRegistrationResponse;
import com.core.linkup.reservation.reservation.response.ReservationResponse;
import com.core.linkup.reservation.reservation.response.CompanyMembershipReservationResponse;
import com.core.linkup.reservation.reservation.response.IndividualMembershipReservationListResponse;

import java.util.List;

@Converter
public class ReservationConverter {

    // 기업 + 기업 멤버십 응답
    public CompanyMembershipRegistrationResponse toCompanyMembershipRegistrationResponse(
            CompanyResponse companyResponse,
            CompanyMembershipResponse companyMembershipResponse) {

        return CompanyMembershipRegistrationResponse.builder()
                .company(companyResponse)
                .companyMembership(companyMembershipResponse)
                .build();
    }

    // 기업 멤버십 + 예약 응답 (생성 & 조회)
    public CompanyMembershipReservationResponse toCompanyMembershipReservationResponse(
            CompanyMembershipResponse companyMembership,
            List<ReservationResponse> reservationResponses) {

        return CompanyMembershipReservationResponse.builder()
                .membership(companyMembership)
                .reservations(reservationResponses)
                .build();
    }

    // 개인 멤버십 + 예약 응답 (생성 & 조회)
    public IndividualMembershipReservationListResponse toIndividualMembershipReservationListResponse(
            IndividualMembershipResponse membership,
            List<ReservationResponse> reservations){

        return IndividualMembershipReservationListResponse.builder()
                .membership(membership)
                .reservations(reservations)
                .build();
    }

    // 개인 멤버십 + 예약(1개)
//    public IndividualMembershipReservationResponse toIndividualMembershipReservationResponse(
//            IndividualMembershipResponse membership,
//            ReservationResponse reservation){
//
//        return IndividualMembershipReservationResponse.builder()
//                .membership(membership)
//                .reservation(reservation)
//                .build();
//    }

    // 예약 응답 (좌석 & 공간)
    public ReservationResponse toReservationResponse(
            Reservation reservation, SeatSpace seatSpace){

        return ReservationResponse.builder()
                .type(reservation.getType().getDescription())
                .startDate(reservation.getStartDate().toLocalDate())
                .startTime(reservation.getStartDate().toLocalTime())
                .endDate(reservation.getEndDate().toLocalDate())
                .endTime(reservation.getEndDate().toLocalTime())
                .price(reservation.getPrice())
                .seatType(seatSpace.getType().getTypeName())
                .seatCode(seatSpace.getCode())
                .build();
    }
}
