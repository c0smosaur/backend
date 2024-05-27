package com.core.linkup.reservation.reservation.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationRequest {

    private String type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long price;
    private Long seatId;

}
