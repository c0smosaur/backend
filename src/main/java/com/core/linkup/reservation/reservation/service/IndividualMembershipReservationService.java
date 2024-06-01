package com.core.linkup.reservation.reservation.service;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.member.entity.Member;
import com.core.linkup.office.entity.OfficeBuilding;
import com.core.linkup.office.entity.SeatSpace;
import com.core.linkup.office.repository.OfficeRepository;
import com.core.linkup.office.repository.SeatSpaceRepository;
import com.core.linkup.reservation.membership.individual.converter.IndividualMembershipConverter;
import com.core.linkup.reservation.membership.individual.entity.IndividualMembership;
import com.core.linkup.reservation.membership.individual.repository.IndividualMembershipRepository;
import com.core.linkup.reservation.membership.individual.service.IndividualMembershipService;
import com.core.linkup.reservation.reservation.converter.ReservationConverter;
import com.core.linkup.reservation.reservation.entity.Reservation;
import com.core.linkup.reservation.reservation.entity.enums.ReservationStatus;
import com.core.linkup.reservation.reservation.entity.enums.ReservationType;
import com.core.linkup.reservation.reservation.repository.ReservationRepository;
import com.core.linkup.reservation.reservation.request.IndividualMembershipRegistrationRequest;
import com.core.linkup.reservation.reservation.request.ReservationRequest;
import com.core.linkup.reservation.reservation.response.MembershipReservationListResponse;
import com.core.linkup.reservation.reservation.response.MembershipResponse;
import com.core.linkup.reservation.reservation.response.ReservationResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndividualMembershipReservationService {

    private final SeatSpaceRepository seatSpaceRepository;
    private final OfficeRepository officeRepository;
    private final ReservationRepository reservationRepository;
    private final IndividualMembershipRepository individualMembershipRepository;

    private final IndividualMembershipService individualMembershipService;
    private final ReservationService reservationService;

    private final ReservationConverter reservationConverter;
    private final IndividualMembershipConverter individualMembershipConverter;

    // (생성. 응답 변환) 개인 멤버십, 예약 생성
    @Transactional
    public MembershipReservationListResponse registerIndividualMembership(
            IndividualMembershipRegistrationRequest requests, Member member, Long officeId) {
        validateOfficeLocation(requests, officeId);
        IndividualMembership membership =
                individualMembershipService.saveIndividualMembership(requests.getMembership(), member);
        List<ReservationResponse> reservationResponses =
                createReservationResponses(requests.getReservations(), membership);
        return reservationConverter.toMembershipReservationListResponse(
                individualMembershipConverter.toMembershipResponse(membership),
                reservationResponses);
    }

    // (검증) 개인 멤버십 요청의 지점과 전달받은 지점 일치하는지 검증
    private void validateOfficeLocation(IndividualMembershipRegistrationRequest requests, Long officeId) {
        OfficeBuilding officeBuilding = officeRepository.findFirstById(officeId);
        if (!requests.getMembership().getLocation().equals(officeBuilding.getLocation())) {
            throw new BaseException(BaseResponseStatus.INVALID_OFFICE_LOCATION);
        }
    }

    // (생성, 응답 변환) 개인 멤버십 생성 요청의 예약 응답 생성
    private List<ReservationResponse> createReservationResponses(
            List<ReservationRequest> requests, IndividualMembership membership) {
        return requests.stream()
                .map(request -> {
                    Reservation reservation = reservationService.saveReservation(request, membership);
                    SeatSpace seatSpace = seatSpaceRepository.findFirstById(reservation.getSeatId());
                    return reservationConverter.toReservationResponse(reservation, seatSpace);
                })
                .toList();
    }

    // (생성) 개인 멤버십 예약 추가
    @Transactional
    public MembershipReservationListResponse addIndividualReservations(
            Member member, List<ReservationRequest> requests, Long membershipId){
        IndividualMembership individualMembership = individualMembershipRepository.findFirstById(membershipId);
        List<ReservationResponse> reservationResponses =
                createReservationResponses(requests, individualMembership);
        return reservationConverter.toMembershipReservationListResponse(
                individualMembershipConverter.toMembershipResponse(individualMembership),
                reservationResponses);
    }

    // (조회) 개인 멤버십 전체 조회
    // null이면 빈 리스트
    public List<MembershipResponse> getAllIndividualMemberships(Member member){
        List<IndividualMembership> memberships =
                individualMembershipRepository.findAllByMemberIdOrderByCreatedDesc(member.getId());
        if (memberships.isEmpty()){
            return new ArrayList<>();
        } else {
            return memberships.stream().map(
                    individualMembershipConverter::toMembershipResponse).toList();
        }
    }

    // (조회, 응답 생성) 개인 멤버십 전체 조회, 예약 전체 조회
    // null이면 빈 리스트
    public List<MembershipReservationListResponse> getAllIndividualMembershipsAndReservations(
            Member member) {
        List<IndividualMembership> memberships =
                individualMembershipRepository.findAllByMemberIdOrderByCreatedDesc(member.getId());
        if (memberships.isEmpty()){
            return new ArrayList<>();
        } else {
            return memberships.stream()
                    .map(membership -> {
                        List<ReservationResponse> reservationResponses =
                                reservationService.getReservationResponses(member, membership);
                        MembershipResponse membershipResponse =
                                individualMembershipConverter.toMembershipResponse(membership);
                        return reservationConverter.toMembershipReservationListResponse(
                                membershipResponse, reservationResponses);
                    }).toList();
        }
    }

    // (조회) 사용자의 개인 멤버십(단수)에 속한 예약 전체 조회
    // null 이면 404
    public List<ReservationResponse> getReservationsForIndividualMembership(
            Member member, Long individualMembershipId){
        IndividualMembership individualMembership =
                individualMembershipRepository.findFirstById(individualMembershipId);
        return reservationService.getReservationResponses(member, individualMembership);
    }

    // (조회) 개별 예약 조회
    // null이면 404
    public ReservationResponse getReservationForIndividualMembership(
            Member member, Long membershipId, Long reservationId){
        if (reservationRepository.existsById(reservationId)){
            IndividualMembership individualMembership =
                    individualMembershipRepository.findFirstById(membershipId);
            return reservationService.getReservationResponseForMembership(member, individualMembership, reservationId);
        } else {
            throw new BaseException(BaseResponseStatus.DOES_NOT_EXIST);
        }
    }

    // (수정) 개인 멤버십 지정석 수정
    public ReservationResponse updateDesignatedReservation(ReservationRequest request,
                                                           Long reservationId,
                                                           Long membershipId) {

        Reservation reservation = reservationRepository.findFirstById(reservationId);
        IndividualMembership individualMembership = individualMembershipRepository.findFirstById(membershipId);

        if (reservation.getType().equals(ReservationType.DESIGNATED_SEAT)){
            Reservation updatedReservation =
                    reservationConverter.updateOriginalDesignatedReservation(request, reservation);
            reservationRepository.save(updatedReservation);
            Reservation newReservation = reservationService.saveReservation(request, individualMembership);
            SeatSpace seatSpace = seatSpaceRepository.findFirstById(newReservation.getSeatId());
            return reservationConverter.toReservationResponse(newReservation, seatSpace);
        } else {
            Reservation updatedReservation = reservationConverter.updateIndividualReservation(
                    request, reservation);
            reservationRepository.save(updatedReservation);
            SeatSpace seatSpace = seatSpaceRepository.findFirstById(updatedReservation.getSeatId());
            return reservationConverter.toReservationResponse(updatedReservation, seatSpace);
        }
    }

    // (삭제) 개별 예약 삭제
    @Transactional
    public boolean deleteReservationForIndividualMembership(Member member, Long membershipId, Long reservationId){
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(
                () -> new BaseException(BaseResponseStatus.DOES_NOT_EXIST));
        IndividualMembership individualMembership =
                individualMembershipRepository.findFirstById(membershipId);
        if (individualMembership.getMemberId().equals(member.getId())){
            reservation.setStatus(ReservationStatus.CANCELED);
            return true;
        } else {
            throw new BaseException(BaseResponseStatus.INVALID_REQUEST);
        }
    }

}
