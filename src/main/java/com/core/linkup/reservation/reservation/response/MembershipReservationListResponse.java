package com.core.linkup.reservation.reservation.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class MembershipReservationListResponse {

    private MembershipResponse membership;
    private List<ReservationResponse> reservations;

}
