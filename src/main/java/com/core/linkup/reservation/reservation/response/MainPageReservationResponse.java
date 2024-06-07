package com.core.linkup.reservation.reservation.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
public class MainPageReservationResponse {
    private String membershipType;
    private Long officeId;
    private String location;
    private Long reservationId;
    private String reservationType;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    //    private String status;

    private String seatType;
    private String seatCode;
}
