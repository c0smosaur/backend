package com.core.linkup.reservation.reservation.service;

import com.core.linkup.common.entity.BaseMembershipEntity;
import com.core.linkup.office.entity.SeatSpace;
import com.core.linkup.office.repository.SeatSpaceRepository;
import com.core.linkup.reservation.membership.individual.entity.IndividualMembership;
import com.core.linkup.reservation.reservation.entity.Reservation;
import com.core.linkup.reservation.reservation.entity.enums.ReservationType;
import com.core.linkup.reservation.reservation.repository.ReservationRepository;
import com.core.linkup.reservation.reservation.request.ReservationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final SeatSpaceRepository seatSpaceRepository;
    private final ReservationRepository reservationRepository;

    public Reservation saveReservation(ReservationRequest request,
                                        BaseMembershipEntity membership){

        SeatSpace seat = seatSpaceRepository.findFirstById(request.getSeatId());
        ReservationType reservationType = ReservationType.fromKor(request.getType());

        LocalDateTime startDate = LocalDateTime.of(request.getStartDate(), request.getStartTime());
        LocalDateTime endDate = LocalDateTime.of(request.getEndDate(), request.getEndTime());

        if (membership.getClass().equals(IndividualMembership.class)) {
            Reservation reservation = Reservation.builder()
                    .type(reservationType)
                    .startDate(startDate)
                    .endDate(endDate)
                    .price(request.getPrice())
                    .individualMembershipId(membership.getId())
                    .seatId(seat.getId())
                    .build();
            return reservationRepository.save(reservation);
        } else {
            Reservation reservation = Reservation.builder()
                    .type(reservationType)
                    .startDate(startDate)
                    .endDate(endDate)
                    .price(request.getPrice())
                    .companyMembershipId(membership.getId())
                    .seatId(seat.getId())
                    .build();
            return reservationRepository.save(reservation);
        }
    }

}