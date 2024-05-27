package com.core.linkup.reservation.reservation.response;

import com.core.linkup.reservation.membership.individual.response.IndividualMembershipResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class IndividualMembershipRegistrationResponse {

    private IndividualMembershipResponse membership;
    private List<ReservationResponse> reservations;
}
