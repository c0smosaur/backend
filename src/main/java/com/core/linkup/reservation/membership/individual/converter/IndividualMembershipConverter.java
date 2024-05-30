package com.core.linkup.reservation.membership.individual.converter;

import com.core.linkup.common.annotation.Converter;
import com.core.linkup.reservation.membership.individual.entity.IndividualMembership;
import com.core.linkup.reservation.membership.individual.response.IndividualMembershipResponse;

@Converter
public class IndividualMembershipConverter {

    // 개인 멤버십 응답
    public IndividualMembershipResponse toIndividualMembershipResponse(
            IndividualMembership individualMembership) {

        return IndividualMembershipResponse.builder()
                .id(individualMembership.getId())
                .location(individualMembership.getLocation())
                .price(individualMembership.getPrice())
                .type(individualMembership.getType().getName())
                .duration(individualMembership.getDuration())
                .startDate(individualMembership.getStartDate().toLocalDate())
                .endDate(individualMembership.getEndDate().toLocalDate())
                .memberId(individualMembership.getMemberId())
                .build();

    }
}
