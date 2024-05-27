package com.core.linkup.reservation.reservation.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ReservationResponse {

    private String type;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long price;

    private String seatType;
    private String seatCode;

}
