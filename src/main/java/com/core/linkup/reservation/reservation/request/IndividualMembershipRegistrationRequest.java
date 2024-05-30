package com.core.linkup.reservation.reservation.request;

import com.core.linkup.reservation.membership.individual.request.IndividualMembershipRequest;
import lombok.Getter;

import java.util.List;

@Getter
public class IndividualMembershipRegistrationRequest {

    private IndividualMembershipRequest membership;
    private List<ReservationRequest> reservations;


}
