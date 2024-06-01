package com.core.linkup.reservation.reservation.service;

import com.core.linkup.member.entity.Member;
import com.core.linkup.reservation.membership.company.converter.CompanyMembershipConverter;
import com.core.linkup.reservation.membership.company.entity.CompanyMembership;
import com.core.linkup.reservation.membership.company.repository.CompanyMembershipRepository;
import com.core.linkup.reservation.membership.company.service.CompanyMembershipService;
import com.core.linkup.reservation.reservation.converter.ReservationConverter;
import com.core.linkup.reservation.reservation.request.CompanyMembershipRegistrationRequest;
import com.core.linkup.reservation.reservation.response.CompanyMembershipRegistrationResponse;
import com.core.linkup.reservation.reservation.response.MembershipReservationListResponse;
import com.core.linkup.reservation.reservation.response.MembershipResponse;
import com.core.linkup.reservation.reservation.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyMembershipReservationService {

    private final CompanyMembershipRepository companyMembershipRepository;

    private final CompanyMembershipService companyMembershipService;
    private final ReservationService reservationService;

    private final ReservationConverter reservationConverter;
    private final CompanyMembershipConverter companyMembershipConverter;

    // (생성, 응답 변환) 기업 문의하기 -> 기업, 기업 멤버십 생성
    public CompanyMembershipRegistrationResponse registerCompanyMembership(CompanyMembershipRegistrationRequest request) {
        return companyMembershipService.registerCompanyMembership(request);
    }

    // 사용자의 단일 기업 멤버십
    // null이면 404
    public MembershipResponse getCompanyMembership(Member member) {
        if (member.getCompanyMembershipId()==null){
//            return reservationConverter.emptyMembershipResponse();
            return null;
        }
        return companyMembershipConverter.toMembershipResponse(
                companyMembershipRepository.findFirstById(member.getCompanyMembershipId()));
    }

    // 사용자의 단일 기업 멤버십 + 예약 전체 조회
    // null이면 빈 리스트
    public MembershipReservationListResponse getReservationsForCompanyMembership(Member member){
        if (member.getCompanyMembershipId()==null){
            return null;
        }
        Optional<CompanyMembership> companyMembership =
                companyMembershipRepository.findById(member.getCompanyMembershipId());
        if (companyMembership.isPresent()) {
            List<ReservationResponse> reservationResponses =
                    reservationService.getReservationResponses(member, companyMembership.get());

            return reservationConverter.toMembershipReservationListResponse(
                    companyMembershipConverter.toMembershipResponse(companyMembership.get()),
                    reservationResponses);
        } else {
            return reservationConverter.emptyMembershipReservationListResponse();
        }
    }

    // 기업 멤버십 예약 개별 조회
    // null이면 404
    public ReservationResponse getReservationForCompanyMembership(
            Member member, Long membershipId, Long reservationId){
        CompanyMembership companyMembership = companyMembershipRepository.findFirstById(membershipId);
        return reservationService.getReservationResponseForMembership(member, companyMembership, reservationId);
    }

    // 기업 멤버십 예약 추가 생성
//    public

}
