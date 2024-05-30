package com.core.linkup.reservation.reservation.response;

import com.core.linkup.reservation.membership.company.response.CompanyMembershipResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CompanyMembershipReservationListResponse {

    // 기업 멤버십 예약 정보
    // 기업 멤버십
    // 예약(좌석, 공간)
    private CompanyMembershipResponse membership;
    private List<ReservationResponse> reservations;
}
