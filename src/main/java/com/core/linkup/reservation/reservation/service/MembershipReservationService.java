package com.core.linkup.reservation.reservation.service;

import com.core.linkup.member.entity.Member;
import com.core.linkup.reservation.reservation.response.MainPageReservationResponse;
import com.core.linkup.reservation.reservation.response.MembershipResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MembershipReservationService {

    private final CompanyMembershipReservationService companyMembershipReservationService;
    private final IndividualMembershipReservationService individualMembershipReservationService;

    // 모든 멤버십 목록
    public List<MembershipResponse> getAllMyMemberships(Member member) {
        List<MembershipResponse> individualMembershipResponses =
                individualMembershipReservationService.getAllIndividualMemberships(member);
        List<MembershipResponse> mutableMembershipResponses = new ArrayList<>(individualMembershipResponses);
        MembershipResponse companyMembershipResponse =
                companyMembershipReservationService.getCompanyMembership(member);
        if (companyMembershipResponse!=null) {
            mutableMembershipResponses.add(companyMembershipResponse);
            return mutableMembershipResponses;
        } else {
            return mutableMembershipResponses;
        }
    }

    // 해당 날짜로 된 예약 목록
    public List<MainPageReservationResponse> getAllReservationsOnDate(
            Member member, LocalDate date) {
        List<MainPageReservationResponse> individualReservations =
                individualMembershipReservationService.getReservationsForIndividualMembershipOnDate(member, date);
        List<MainPageReservationResponse> companyReservations =
                companyMembershipReservationService.getReservationsForCompanyMembershipOnDate(member, date);
        List<MainPageReservationResponse> responses = new ArrayList<>(individualReservations);
        responses.addAll(companyReservations);

        return responses;
    }
}
