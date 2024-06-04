package com.core.linkup.reservation.reservation.request;

import lombok.Getter;

@Getter
public class ReservationRequest {

    private String type;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private Long price;
    private Long seatId;

}
