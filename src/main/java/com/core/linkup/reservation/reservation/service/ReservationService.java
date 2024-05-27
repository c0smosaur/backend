package com.core.linkup.reservation.reservation.service;

import com.core.linkup.office.entity.SeatSpace;
import com.core.linkup.office.repository.SeatSpaceRepository;
import com.core.linkup.reservation.membership.individual.entity.IndividualMembership;
import com.core.linkup.reservation.reservation.converter.ReservationConverter;
import com.core.linkup.reservation.reservation.entity.Reservation;
import com.core.linkup.reservation.reservation.entity.enums.ReservationType;
import com.core.linkup.reservation.reservation.repository.ReservationRepository;
import com.core.linkup.reservation.reservation.request.ReservationRequest;
import com.core.linkup.reservation.reservation.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final SeatSpaceRepository seatSpaceRepository;
    private final ReservationConverter reservationConverter;
    private final ReservationRepository reservationRepository;

    public Reservation buildReservation(ReservationRequest request,
                                        IndividualMembership individualMembership){

        SeatSpace seat = seatSpaceRepository.findFirstById(request.getSeatId());

        return Reservation.builder()
                .type(ReservationType.valueOf(request.getType()))
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .price(request.getPrice())
                .individualMembership(individualMembership)
                .seatSpace(seat)
                .build();
    }

    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public ReservationResponse toResponse(Reservation reservation) {
        return reservationConverter.toReservationResponse(reservation);
    }

}
