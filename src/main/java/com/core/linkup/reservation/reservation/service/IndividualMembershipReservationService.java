package com.core.linkup.reservation.reservation.service;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.member.entity.Member;
import com.core.linkup.office.entity.OfficeBuilding;
import com.core.linkup.office.entity.SeatSpace;
import com.core.linkup.office.repository.OfficeRepository;
import com.core.linkup.office.repository.SeatSpaceRepository;
import com.core.linkup.reservation.membership.company.repository.CompanyMembershipRepository;
import com.core.linkup.reservation.membership.company.service.CompanyMembershipService;
import com.core.linkup.reservation.membership.individual.converter.IndividualMembershipConverter;
import com.core.linkup.reservation.membership.individual.entity.IndividualMembership;
import com.core.linkup.reservation.membership.individual.repository.IndividualMembershipRepository;
import com.core.linkup.reservation.membership.individual.response.IndividualMembershipResponse;
import com.core.linkup.reservation.membership.individual.service.IndividualMembershipService;
import com.core.linkup.reservation.reservation.converter.ReservationConverter;
import com.core.linkup.reservation.reservation.entity.Reservation;
import com.core.linkup.reservation.reservation.repository.ReservationRepository;
import com.core.linkup.reservation.reservation.request.IndividualMembershipRegistrationRequest;
import com.core.linkup.reservation.reservation.response.ReservationResponse;
import com.core.linkup.reservation.reservation.response.IndividualMembershipReservationListResponse;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndividualMembershipReservationService {

    private final SeatSpaceRepository seatSpaceRepository;
    private final OfficeRepository officeRepository;
    private final ReservationRepository reservationRepository;
    private final CompanyMembershipRepository companyMembershipRepository;
    private final IndividualMembershipRepository individualMembershipRepository;

    private final CompanyMembershipService companyMembershipService;
    private final IndividualMembershipService individualMembershipService;
    private final ReservationService reservationService;

    private final ReservationConverter reservationConverter;
    private final IndividualMembershipConverter individualMembershipConverter;

    // (생성. 응답 변환) 개인 멤버십, 에약 생성
    public IndividualMembershipReservationListResponse registerIndividualMembership(
            IndividualMembershipRegistrationRequest requests, Member member, Long officeId) {

        validateOfficeLocation(requests, officeId);

        IndividualMembership membership = individualMembershipService.saveIndividualMembership(requests.getMembership(), member);

        List<ReservationResponse> reservationResponses = createReservationResponses(requests, membership);

        return reservationConverter.toIndividualMembershipReservationListResponse(
                individualMembershipConverter.toIndividualMembershipResponse(membership),
                reservationResponses
        );
    }

    // (검증) 기업 멤버십 요청의 지점과 전달받은 지점 일치하는지 검증
    private void validateOfficeLocation(IndividualMembershipRegistrationRequest requests, Long officeId) {
        OfficeBuilding officeBuilding = officeRepository.findFirstById(officeId);
        if (!requests.getMembership().getLocation().equals(officeBuilding.getLocation())) {
            throw new BaseException(BaseResponseStatus.INVALID_OFFICE_LOCATION);
        }
    }

    // (생성, 응답 변환) 기업 멤버십 생성 요청의 예약 생성
    private List<ReservationResponse> createReservationResponses(IndividualMembershipRegistrationRequest requests, IndividualMembership membership) {
        return requests.getReservations().stream()
                .map(request -> {
                    Reservation reservation = reservationService.saveReservation(request, membership);
                    SeatSpace seatSpace = seatSpaceRepository.findFirstById(reservation.getSeatId());
                    return reservationConverter.toReservationResponse(reservation, seatSpace);
                })
                .toList();
    }

    // (조회, 응답 생성) 사용자의 전체 개인 멤버십과 예약 조회
    public List<IndividualMembershipReservationListResponse> viewAllIndividualMembershipAndReservations(Member member) {
        List<IndividualMembership> memberships =
                individualMembershipRepository.findAllByMemberIdOrderByCreatedAtDesc(member.getId());

        return memberships.stream()
                .map(membership -> {
                    List<ReservationResponse> reservationResponses = getReservationResponses(member, membership);
                    IndividualMembershipResponse membershipResponse = individualMembershipConverter.toIndividualMembershipResponse(membership);
                    return reservationConverter.toIndividualMembershipReservationListResponse(membershipResponse, reservationResponses);
                })
                .toList();
    }

    // (조회, 응답 생성) 예약 튜플을 응답 형태로 변환
    private List<ReservationResponse> getReservationResponses(Member member, IndividualMembership membership) {
        List<Tuple> tuples = reservationRepository.findAllReservationAndSeatByMembershipId(membership.getId(), member.getId());
        return tuples.stream()
                .map(tuple -> {
                    Reservation reservation = tuple.get(0, Reservation.class);
                    SeatSpace seatSpace = tuple.get(1, SeatSpace.class);
                    return reservationConverter.toReservationResponse(reservation, seatSpace);
                })
                .toList();
    }



}
