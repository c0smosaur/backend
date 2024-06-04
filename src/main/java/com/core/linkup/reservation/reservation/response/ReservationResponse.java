package com.core.linkup.reservation.reservation.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
public class ReservationResponse {

    private Long id;
    private String type;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
//    private String status;
    private Long price;

    private String seatType;
    private String seatCode;

}
