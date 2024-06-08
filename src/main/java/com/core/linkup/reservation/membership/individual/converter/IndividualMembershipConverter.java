package com.core.linkup.reservation.membership.individual.converter;

import com.core.linkup.common.annotation.Converter;
import com.core.linkup.member.entity.Member;
import com.core.linkup.reservation.membership.individual.entity.IndividualMembership;
import com.core.linkup.reservation.membership.individual.entity.enums.MembershipType;
import com.core.linkup.reservation.membership.individual.request.IndividualMembershipRequest;
import com.core.linkup.reservation.reservation.response.MembershipResponse;

@Converter
public class IndividualMembershipConverter {

    public IndividualMembership toIndividualMembershipEntity(Long officeId,
                                                             IndividualMembershipRequest
                                                                     request, Member member) {
        MembershipType membershipType = MembershipType.fromKor(request.getType());
        return IndividualMembership.builder()
                .location(request.getLocation())
                .officeId(officeId)
                .type(membershipType)
                .duration(request.getDuration())
                .startDate(request.getStartDate().atStartOfDay())
                .endDate(request.getEndDate().atStartOfDay())
                .price(request.getPrice())
                .memberId(member.getId())
                .build();
    }

    public MembershipResponse toMembershipResponse(IndividualMembership individualMembership){
        return MembershipResponse.builder()
                .type(individualMembership.getType().getName())
                .id(individualMembership.getId())
                .location(individualMembership.getLocation())
                .officeId(individualMembership.getOfficeId())
                .price(individualMembership.getPrice())
                .duration(individualMembership.getDuration())
                .startDate(individualMembership.getStartDate().toLocalDate())
                .endDate(individualMembership.getEndDate().toLocalDate())
                .memberId(individualMembership.getMemberId())
                .build();
    }
}
