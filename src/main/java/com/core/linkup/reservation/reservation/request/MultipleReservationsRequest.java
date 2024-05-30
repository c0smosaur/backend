package com.core.linkup.reservation.reservation.request;

import lombok.Getter;

import java.util.List;

@Getter
public class MultipleReservationsRequest {

    private List<ReservationRequest> reservations;
}
