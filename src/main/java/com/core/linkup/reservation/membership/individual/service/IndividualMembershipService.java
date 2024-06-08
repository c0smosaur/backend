package com.core.linkup.reservation.membership.individual.service;

import com.core.linkup.member.entity.Member;
import com.core.linkup.reservation.membership.individual.converter.IndividualMembershipConverter;
import com.core.linkup.reservation.membership.individual.entity.IndividualMembership;
import com.core.linkup.reservation.membership.individual.repository.IndividualMembershipRepository;
import com.core.linkup.reservation.membership.individual.request.IndividualMembershipRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndividualMembershipService {

    private final IndividualMembershipRepository individualMembershipRepository;
    private final IndividualMembershipConverter individualMembershipConverter;

    public IndividualMembership saveIndividualMembership(Long officeId,
                                                         IndividualMembershipRequest request,
                                                         Member member) {
        return individualMembershipRepository.save(
                individualMembershipConverter.toIndividualMembershipEntity(officeId, request, member));
    }

}
