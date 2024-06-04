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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

        LocalDate startDate = LocalDate.parse(request.getStartDate());
        LocalDate endDate = LocalDate.parse(request.getEndDate());

        if (membership.getClass().equals(IndividualMembership.class)) {
            List<LocalDateTime> dates = getLocalDateTime(request);

            return Reservation.builder()
                    .type(reservationType)
                    .startDate(dates.get(0))
                    .endDate(dates.get(1))
                    .status(ReservationStatus.RESERVED)
                    .price(request.getPrice())
                    .individualMembershipId(membership.getId())
                    .seatId(seatSpace.getId())
                    .build();
        } else {
            List<LocalDateTime> dates = getLocalDateTime(request);
            return Reservation.builder()
                    .type(reservationType)
                    .startDate(dates.get(0))
                    .endDate(dates.get(1))
                    .status(ReservationStatus.RESERVED)
                    .price(request.getPrice())
                    .companyMembershipId(membership.getId())
                    .seatId(seatSpace.getId())
                    .build();
        }
    }

    public List<LocalDateTime> getLocalDateTime(ReservationRequest request) {
        List<LocalDateTime> localDateTimeList = new ArrayList<>();

        if (request.getStartTime().isEmpty() && request.getEndTime().isEmpty()) {
            LocalDateTime startDate = LocalDate.parse(request.getStartDate()).atStartOfDay();
            LocalDateTime endDate = LocalDate.parse(request.getEndDate()).atStartOfDay();
            localDateTimeList.add(startDate);
            localDateTimeList.add(endDate);
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

            LocalDateTime startDate = LocalDateTime.of(
                    LocalDate.parse(request.getStartDate()),
                    LocalTime.parse(request.getStartTime(), formatter));
            LocalDateTime endDate = LocalDateTime.of(
                    LocalDate.parse(request.getEndDate()),
                    LocalTime.parse(request.getEndTime(), formatter));

            localDateTimeList.add(startDate);
            localDateTimeList.add(endDate);
        }
        return localDateTimeList;
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
                .id(reservation.getId())
                .type(reservation.getType().getName())
                .startDate(String.valueOf(reservation.getStartDate().toLocalDate()))
                .startTime(String.valueOf(reservation.getStartDate().toLocalTime()))
                .endDate(String.valueOf(reservation.getEndDate().toLocalDate()))
                .endTime(String.valueOf(reservation.getEndDate().toLocalTime()))
//                .status(reservation.getStatus().getDescription())
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
                .isAvailable(isReserved)
                .build();
    }

    // 예약 수정
    public Reservation updateOriginalDesignatedReservation(ReservationRequest request, Reservation originalReservation){
        return originalReservation.toBuilder()
                .endDate(LocalDate.parse(request.getStartDate()).atStartOfDay())
                .build();
    }

    public Reservation updateReservation(ReservationRequest request, Reservation originalReservation){
        // 자율 좌석 변경
        if (request.getType().equals(ReservationType.TEMPORARY_SEAT.getName())) {
            return originalReservation.toBuilder()
                    .seatId(request.getSeatId())
                    .build();
        // 공간 변경
        } else {
            LocalDateTime startDate = LocalDateTime.of(
                    LocalDate.parse(request.getStartDate()),
                    LocalTime.parse(request.getStartTime()));
            LocalDateTime endDate = LocalDateTime.of(
                    LocalDate.parse(request.getEndDate()),
                    LocalTime.parse(request.getEndTime()));
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
