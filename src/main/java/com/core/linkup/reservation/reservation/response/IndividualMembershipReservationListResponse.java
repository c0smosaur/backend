package com.core.linkup.reservation.reservation.response;

import com.core.linkup.reservation.membership.individual.response.IndividualMembershipResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class IndividualMembershipReservationListResponse {

    // 개인 멤버십 예약 정보
    // 개인 멤버십
    // 예약(리스트)(좌석, 공간)
    private IndividualMembershipResponse membership;
    private List<ReservationResponse> reservations;

}