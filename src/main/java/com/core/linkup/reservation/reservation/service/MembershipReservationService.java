package com.core.linkup.reservation.reservation.service;

import com.core.linkup.member.entity.Member;
import com.core.linkup.reservation.reservation.response.MembershipReservationListResponse;
import com.core.linkup.reservation.reservation.response.MembershipResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MembershipReservationService {

    private final CompanyMembershipReservationService companyMembershipReservationService;
    private final IndividualMembershipReservationService individualMembershipReservationService;

    public List<MembershipReservationListResponse> getAllMyMembershipsAndReservations(Member member){
        List<MembershipReservationListResponse> individualResponses =
                individualMembershipReservationService.getAllIndividualMembershipsAndReservations(
                        member);
        MembershipReservationListResponse companyResponse =
                companyMembershipReservationService.getReservationsForCompanyMembership(member);

        individualResponses.add(companyResponse);
        return individualResponses;
    }

    // 모든 멤버십 목록
    public List<MembershipResponse> getAllMyMemberships(Member member) {
        List<MembershipResponse> individualMembershipResponses =
                individualMembershipReservationService.getAllIndividualMemberships(member);
        MembershipResponse companyMembershipResponse =
                companyMembershipReservationService.getCompanyMembership(member);
        if (companyMembershipResponse!=null) {
            individualMembershipResponses.add(companyMembershipResponse);
            return individualMembershipResponses;
        } else {
            return individualMembershipResponses;
        }
    }

}
