package com.core.linkup.reservation.reservation.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class MembershipReservationResponse {
    private String location;
    private LocalDate endDate;
    private String seatType;
    private String seatCode;
}
