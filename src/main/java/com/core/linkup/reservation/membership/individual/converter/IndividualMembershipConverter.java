package com.core.linkup.reservation.membership.individual.converter;

import com.core.linkup.common.annotation.Converter;
import com.core.linkup.reservation.membership.individual.entity.IndividualMembership;
import com.core.linkup.reservation.membership.individual.response.IndividualMembershipResponse;

@Converter
public class IndividualMembershipConverter {

    public IndividualMembershipResponse toIndividualMembershipResponse(
            IndividualMembership individualMembership) {

        return IndividualMembershipResponse.builder()
                .id(individualMembership.getId())
                .location(individualMembership.getLocation())
                .price(individualMembership.getPrice())
                .type(individualMembership.getType().getName())
                .duration(individualMembership.getDuration())
                .startDate(individualMembership.getStartDate())
                .endDate(individualMembership.getEndDate())
                .memberId(individualMembership.getMember().getId())
                .build();

    }
}
