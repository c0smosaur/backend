package com.core.linkup.reservation.reservation.service;

import com.core.linkup.common.entity.BaseMembershipEntity;
import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.member.entity.Member;
import com.core.linkup.office.entity.SeatSpace;
import com.core.linkup.office.entity.enums.SeatSpaceType;
import com.core.linkup.office.repository.SeatSpaceRepository;
import com.core.linkup.reservation.membership.individual.entity.IndividualMembership;
import com.core.linkup.reservation.reservation.converter.ReservationConverter;
import com.core.linkup.reservation.reservation.entity.Reservation;
import com.core.linkup.reservation.reservation.entity.enums.ReservationType;
import com.core.linkup.reservation.reservation.repository.ReservationRepository;
import com.core.linkup.reservation.reservation.request.ReservationRequest;
import com.core.linkup.reservation.reservation.response.ReservationResponse;
import com.core.linkup.reservation.reservation.response.SeatSpaceResponse;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final SeatSpaceRepository seatSpaceRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationConverter reservationConverter;

    public Reservation saveReservation(ReservationRequest request,
                                        BaseMembershipEntity membership){

        SeatSpace seat = seatSpaceRepository.findFirstById(request.getSeatId());
        return reservationRepository.save(
                reservationConverter.toReservationEntity(request, membership, seat));
    }

    // (조회, 응답 생성) 예약 튜플을 응답 형태로 변환
    public List<ReservationResponse> getReservationResponses(Member member, BaseMembershipEntity membership) {

        if (membership instanceof IndividualMembership){
            List<Tuple> tuples = reservationRepository.findAllReservationAndSeatByIndividualMembershipId(
                    membership.getId(), member.getId());
            return getReservationResponsesFromTuple(tuples);
        } else {
            List<Tuple> tuples = reservationRepository.findAllReservationsAndSeatByCompanyMembershipId(
                    membership.getId(), member.getId());
            return getReservationResponsesFromTuple(tuples);
        }
    }

    private List<ReservationResponse> getReservationResponsesFromTuple(List<Tuple> tuples) {
        return tuples.stream()
                .map(tuple -> {
                    Reservation reservation = tuple.get(0, Reservation.class);
                    SeatSpace seatSpace = tuple.get(1, SeatSpace.class);
                    return reservationConverter.toReservationResponse(reservation, seatSpace);
                })
                .toList();
    }

    public List<ReservationResponse> createReservationResponses(
            List<ReservationRequest> requests, BaseMembershipEntity membership) {
        return requests.stream()
                .map(request -> {
                    Reservation reservation = saveReservation(request, membership);
                    SeatSpace seatSpace = seatSpaceRepository.findFirstById(reservation.getSeatId());
                    return reservationConverter.toReservationResponse(reservation, seatSpace);
                })
                .toList();
    }

    // (조회) 개별 예약 조회
    public ReservationResponse getReservationResponseForMembership(
            Member member, BaseMembershipEntity membership, Long reservationId){

        if (membership instanceof IndividualMembership){
            Tuple tuple = reservationRepository.findReservationAndSeatByReservationIdAndMembershipId(
                    member.getId(), membership.getId(), reservationId);
            if (tuple!=null){
                return reservationConverter.toReservationResponse(
                        tuple.get(0, Reservation.class), tuple.get(1, SeatSpace.class));
            } else {
                throw new BaseException(BaseResponseStatus.DOES_NOT_EXIST);
            }
        } else {
            Tuple tuple = reservationRepository.findReservationAndSeatByReservationIdAndCompanyMembershipId(
                    member.getId(), membership.getId(), reservationId);
            if (tuple!=null){
                return reservationConverter.toReservationResponse(
                        tuple.get(0, Reservation.class), tuple.get(1, SeatSpace.class));
            } else {
                throw new BaseException(BaseResponseStatus.DOES_NOT_EXIST);
            }
        }
    }

    public ReservationResponse updateReservationByType(ReservationRequest request,
                                                       Reservation oldReservation,
                                                       BaseMembershipEntity membership){
        if (oldReservation.getType().equals(ReservationType.DESIGNATED_SEAT)){
            Reservation updatedReservation =
                    reservationConverter.updateOriginalDesignatedReservation(request, oldReservation);
            reservationRepository.save(updatedReservation);
            Reservation newReservation = saveReservation(request, membership);
            SeatSpace seatSpace = seatSpaceRepository.findFirstById(newReservation.getSeatId());
            return reservationConverter.toReservationResponse(newReservation, seatSpace);
        } else {
            Reservation updatedReservation = reservationConverter.updateReservation(
                    request, oldReservation);
            reservationRepository.save(updatedReservation);
            SeatSpace seatSpace = seatSpaceRepository.findFirstById(updatedReservation.getSeatId());
            return reservationConverter.toReservationResponse(updatedReservation, seatSpace);
        }
    }

    // 잔여 좌석 조회
    public List<SeatSpaceResponse> getAvailableSeatSpaces(Long officeId, String type, String start, String end) {

        LocalDateTime startDate = LocalDateTime.parse(start);
        LocalDateTime endDate = LocalDateTime.parse(end);

        List<SeatSpace> allSeatSpaces =
                seatSpaceRepository.findAllByOfficeIdAndType(officeId, SeatSpaceType.fromKor(type));
        List<SeatSpace> availableSeatSpaces =
                reservationRepository.findAllSeatSpacesByOfficeIdAndType(
                        officeId, String.valueOf(SeatSpaceType.fromKor(type)), startDate, endDate);

        // 전체 좌석 리스트와 잔여 좌석 리스트
        // 잔여 좌석 리스트에 있으면 해당 좌석 true
        return allSeatSpaces.stream().map(
                seatSpace -> {
                    return SeatSpaceResponse.builder()
                            .id(seatSpace.getId())
                            .code(seatSpace.getCode())
                            .type(seatSpace.getType().getTypeName())
                            .isAvailable(availableSeatSpaces.contains(seatSpace))
                            .build();
                }
        ).toList();
    }
}