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
import com.core.linkup.reservation.reservation.response.MainPageReservationResponse;
import com.core.linkup.reservation.reservation.response.ReservationResponse;
import com.core.linkup.reservation.reservation.response.SeatSpaceResponse;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public List<ReservationResponse> getReservationResponsesWithMembership(Member member, BaseMembershipEntity membership) {

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

    public List<MainPageReservationResponse> getMainPageReservationResponseFromTuple(List<Tuple> tuples) {
        return !tuples.isEmpty() ?
                tuples.stream()
                    .map(tuple -> {
                        BaseMembershipEntity membership = tuple.get(0, IndividualMembership.class);
                        Reservation reservation = tuple.get(1, Reservation.class);
                        SeatSpace seatSpace = tuple.get(2, SeatSpace.class);
                        return reservationConverter.toMainPageReservationResponse(
                                membership, reservation, seatSpace);
                    })
                .toList() :
                new ArrayList<>();
    }

    // 예약 저장 후 응답으로 변환
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

    // 예약 타입에 따라 예약 수정
    // 기업 지정석 || 지정석 : 기존의 예약 종료일 오늘로 변환 후 상태 CANCELED로 수정, 새로운 예약 객체 생성
    // 자율좌석 || 공간 : 자율좌석은 좌석만 변경, 공간은 시간까지 변경
    public ReservationResponse updateReservationByType(ReservationRequest request,
                                                       Reservation oldReservation,
                                                       BaseMembershipEntity membership){
        if (oldReservation.getType().equals(ReservationType.DESIGNATED_SEAT)
                || oldReservation.getType().equals(ReservationType.COMPANY_DESIGNATED_SEAT)){
            // 기업 지정석이나 지정석
            Reservation updatedReservation =
                    reservationConverter.updateOriginalDesignatedReservation(request, oldReservation);
            reservationRepository.save(updatedReservation);
            Reservation newReservation = saveReservation(request, membership);
            SeatSpace seatSpace = seatSpaceRepository.findFirstById(newReservation.getSeatId());
            return reservationConverter.toReservationResponse(newReservation, seatSpace);
        } else {
            // 자율 좌석이나 공간
            Reservation updatedReservation = reservationConverter.updateReservation(
                    request, oldReservation);
            reservationRepository.save(updatedReservation);
            SeatSpace seatSpace = seatSpaceRepository.findFirstById(updatedReservation.getSeatId());
            return reservationConverter.toReservationResponse(updatedReservation, seatSpace);
        }
    }

    // 잔여 좌석 조회
    public List<SeatSpaceResponse> getAvailableSeatSpaces(
            Long officeId, String type, String start, String startTime, String end, String endTime) {

        if (startTime==null&&endTime==null){
            LocalDateTime startDate = LocalDate.parse(start).atStartOfDay();
            LocalDateTime endDate = LocalDate.parse(end).atStartOfDay();

            return getSeatSpacesFromDate(officeId, type, startDate, endDate);
        } else {
            LocalDate sDate = LocalDate.parse(start);
            LocalTime sTime = LocalTime.parse(startTime);
            LocalDate eDate = LocalDate.parse(end);
            LocalTime eTime = LocalTime.parse(endTime);

            LocalDateTime startDate = LocalDateTime.of(sDate, sTime);
            LocalDateTime endDate = LocalDateTime.of(eDate, eTime);

            return getSeatSpacesFromDate(officeId, type, startDate, endDate);
        }
    }

    private List<SeatSpaceResponse> getSeatSpacesFromDate(
            Long officeId, String type, LocalDateTime startDate, LocalDateTime endDate) {

        if (type.equals(SeatSpaceType.CONF4.getTypeName())
        || type.equals(SeatSpaceType.CONF8.getTypeName())
        || type.equals(SeatSpaceType.CONFERENCE_ROOM.getTypeName())
        || type.equals(SeatSpaceType.STUDIO.getTypeName())){

            List<SeatSpaceResponse> responses = new ArrayList<>();
            // 해당 건물의 공간 조회
            List<SeatSpace> allSeatSpaces =
                    seatSpaceRepository.findAllByOfficeIdAndType(officeId, SeatSpaceType.fromKor(type));

            for (SeatSpace seatSpace : allSeatSpaces){
                // 한 공간에 대한 특정 날짜의 예약들
                List<Reservation> reservations =
                        reservationRepository.findAllReservationsBySeatIdAndDateAndType(
                                seatSpace.getId(), startDate, SeatSpaceType.fromKor(type));

                List<LocalTime> reservedTimes = reservations.stream()
                        .flatMap(reservation -> {
                            LocalTime startTime = reservation.getStartDate().toLocalTime();
                            LocalTime endTime = reservation.getEndDate().toLocalTime();
                            return Stream.iterate(startTime, time -> time.plusMinutes(30))
                                    .limit(Duration.between(startTime, endTime).toMinutes() / 30);
                        }).toList();

                List<String> am = new ArrayList<>();
                List<String> pm = new ArrayList<>();
                LocalTime time = LocalTime.of(8,0);

                while (time.isBefore(LocalTime.of(21,30))){
                    if (!reservedTimes.contains(time)){
                        if (time.isBefore(LocalTime.NOON)){
                            am.add(time.toString());
                        } else {
                            pm.add(time.toString());
                        }
                    }
                    time = time.plusMinutes(30);

                }
                    if (!reservedTimes.contains(LocalTime.of(21,30))){
                        pm.add(LocalTime.of(21,30).toString());
                    }

                SeatSpaceResponse response = SeatSpaceResponse.builder()
                        .id(seatSpace.getId())
                        .type(seatSpace.getType().getTypeName())
                        .code(seatSpace.getCode())
                        .isAvailable(true)
                        .am(am)
                        .pm(pm)
                        .build();

                responses.add(response);
            }

            return responses;

        } else {
            // 좌석
            List<SeatSpace> allSeatSpaces =
                    seatSpaceRepository.findAllByOfficeIdAndType(officeId, SeatSpaceType.fromKor(type));
            List<SeatSpace> availableSeatSpaces =
                    reservationRepository.findAllSeatSpacesByOfficeIdAndType(
                            officeId, String.valueOf(SeatSpaceType.fromKor(type)), startDate, endDate);

            // 전체 좌석 리스트와 잔여 좌석 리스트
            // 잔여 좌석 리스트에 있으면 해당 좌석 true
            return allSeatSpaces.stream().map(
                    seatSpace -> SeatSpaceResponse.builder()
                            .id(seatSpace.getId())
                            .code(seatSpace.getCode())
                            .type(seatSpace.getType().getTypeName())
                            .isAvailable(availableSeatSpaces.contains(seatSpace))
                            .build()
            ).toList();
        }
    }
}