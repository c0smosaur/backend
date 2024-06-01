package com.core.linkup.reservation.reservation.converter;

import com.core.linkup.common.annotation.Converter;
import com.core.linkup.common.entity.BaseMembershipEntity;
import com.core.linkup.office.entity.SeatSpace;
import com.core.linkup.reservation.membership.company.response.CompanyResponse;
import com.core.linkup.reservation.membership.individual.entity.IndividualMembership;
import com.core.linkup.reservation.reservation.entity.Reservation;
import com.core.linkup.reservation.reservation.entity.enums.ReservationStatus;
import com.core.linkup.reservation.reservation.entity.enums.ReservationType;
import com.core.linkup.reservation.reservation.request.ReservationRequest;
import com.core.linkup.reservation.reservation.response.*;

import java.time.LocalDateTime;
import java.util.List;

@Converter
public class ReservationConverter {

    // 기업 + 기업 멤버십 응답
    public CompanyMembershipRegistrationResponse toCompanyMembershipRegistrationResponse(
            CompanyResponse companyResponse,
            MembershipResponse companyMembershipResponse) {

        return CompanyMembershipRegistrationResponse.builder()
                .company(companyResponse)
                .membership(companyMembershipResponse)
                .build();
    }

    public Reservation toReservationEntity(ReservationRequest request,
                                           BaseMembershipEntity membership,
                                           SeatSpace seatSpace){
        ReservationType reservationType = ReservationType.fromKor(request.getType());

        LocalDateTime startDate = LocalDateTime.of(request.getStartDate(), request.getStartTime());
        LocalDateTime endDate = LocalDateTime.of(request.getEndDate(), request.getEndTime());

        if (membership.getClass().equals(IndividualMembership.class)) {
            return Reservation.builder()
                    .type(reservationType)
                    .startDate(startDate)
                    .endDate(endDate)
                    .status(ReservationStatus.RESERVED)
                    .price(request.getPrice())
                    .individualMembershipId(membership.getId())
                    .seatId(seatSpace.getId())
                    .build();
        } else {
            return Reservation.builder()
                    .type(reservationType)
                    .startDate(startDate)
                    .endDate(endDate)
                    .status(ReservationStatus.RESERVED)
                    .price(request.getPrice())
                    .companyMembershipId(membership.getId())
                    .seatId(seatSpace.getId())
                    .build();
        }
    }

    // 공통 멤버십 응답 + 예약 응답
    public MembershipReservationListResponse toMembershipReservationListResponse(
            MembershipResponse membershipResponse,
            List<ReservationResponse> reservationResponses){
        return MembershipReservationListResponse.builder()
                .membership(membershipResponse)
                .reservations(reservationResponses)
                .build();
    }

    // 예약 응답 (좌석 & 공간)
    public ReservationResponse toReservationResponse(
            Reservation reservation, SeatSpace seatSpace){

        return ReservationResponse.builder()
                .type(reservation.getType().getName())
                .startDate(reservation.getStartDate().toLocalDate())
                .startTime(reservation.getStartDate().toLocalTime())
                .endDate(reservation.getEndDate().toLocalDate())
                .endTime(reservation.getEndDate().toLocalTime())
                .price(reservation.getPrice())
                .seatType(seatSpace.getType().getTypeName())
                .seatCode(seatSpace.getCode())
                .build();
    }

    // 좌석 응답
    public SeatSpaceResponse toSeatSpaceResponse(SeatSpace seatSpace, boolean isReserved){
        return SeatSpaceResponse.builder()
                .id(seatSpace.getId())
                .type(seatSpace.getType().getTypeName())
                .code(seatSpace.getCode())
                .isReserved(isReserved)
                .build();
    }

    // 예약 수정
    public Reservation updateOriginalDesignatedReservation(ReservationRequest request, Reservation originalReservation){
        return originalReservation.toBuilder()
                .endDate(request.getStartDate().atStartOfDay())
                .build();
    }

    public Reservation updateIndividualReservation(ReservationRequest request, Reservation originalReservation){
        // 자율 좌석 변경
        if (request.getType().equals(ReservationType.TEMPORARY_SEAT.getName())) {
            return originalReservation.toBuilder()
                    .seatId(request.getSeatId())
                    .build();
        // 공간 변경
        } else {
            LocalDateTime startDate = LocalDateTime.of(request.getStartDate(), request.getStartTime());
            LocalDateTime endDate = LocalDateTime.of(request.getEndDate(), request.getEndTime());
            return originalReservation.toBuilder()
                    .startDate(startDate)
                    .endDate(endDate)
                    .seatId(request.getSeatId())
                    .price(request.getPrice())
                    .build();
        }
    }

    public ReservationResponse emptyReservationResponse(){
        return ReservationResponse.builder().build();
    }

    public MembershipResponse emptyMembershipResponse() {
        return MembershipResponse.builder().build();
    }

    public MembershipReservationListResponse emptyMembershipReservationListResponse(){
        return MembershipReservationListResponse.builder().build();
    }
}
