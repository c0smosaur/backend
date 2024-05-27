package com.core.linkup.reservation.membership.individual.service;

import com.core.linkup.member.entity.Member;
import com.core.linkup.reservation.membership.individual.converter.IndividualMembershipConverter;
import com.core.linkup.reservation.membership.individual.entity.IndividualMembership;
import com.core.linkup.reservation.membership.individual.entity.enums.MembershipType;
import com.core.linkup.reservation.membership.individual.repository.IndividualMembershipRepository;
import com.core.linkup.reservation.membership.individual.request.IndividualMembershipRequest;
import com.core.linkup.reservation.membership.individual.response.IndividualMembershipResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndividualMembershipService {

    private final IndividualMembershipRepository individualMembershipRepository;
    private final IndividualMembershipConverter individualMembershipConverter;

    public IndividualMembership buildIndividualMembership(IndividualMembershipRequest request,
                                                          Member member) {
        MembershipType membershipType = MembershipType.fromKor(request.getType());
        return IndividualMembership.builder()
                .location(request.getLocation())
                .type(membershipType)
                .duration(request.getDuration())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .price(request.getPrice())
                .member(member)
                .reservations(new ArrayList<>())
                .build();
    }

    public IndividualMembershipResponse toResponse(IndividualMembership individualMembership) {
        return individualMembershipConverter.toIndividualMembershipResponse(individualMembership);
    }

    public IndividualMembership saveIndividualMembership(IndividualMembership individualMembership) {
        return individualMembershipRepository.save(individualMembership);
    }

}
