package com.core.linkup.reservation.reservation.request;

import lombok.Getter;

import java.util.List;

@Getter
public class IndividualMembershipRegistrationRequest {

    private com.core.linkup.reservation.membership.individual.request.IndividualMembershipRequest membership;
    private List<ReservationRequest> reservations;


}
