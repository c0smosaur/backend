package com.core.linkup.reservation.reservation.service;

import com.core.linkup.reservation.membership.individual.request.IndividualMembershipRequest;
import com.core.linkup.reservation.reservation.converter.ReservationConverter;
import com.core.linkup.reservation.reservation.repository.ReservationRepository;
import com.core.linkup.reservation.reservation.request.ReservationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationValidationService {

    private final ReservationRepository reservationRepository;
    private final ReservationConverter reservationConverter;


    // 예약 날짜 검증
    public boolean validateReservationDate(ReservationRequest request){
        // 검증 통과 x 사례
        // 1) 시작일자보다 종료일자가 빠른 경우
        // 2) 종료일자가 현재 일자보다 빠른 경우
        // 3) 시작일자가 현재 날짜보다 빠른 경우
        List<LocalDateTime> dates = reservationConverter.getLocalDateTime(request);
        LocalDateTime startDate = dates.get(0);
        LocalDateTime endDate = dates.get(1);

            return !startDate.isBefore(endDate)
                    || endDate.isBefore(LocalDateTime.now())
                    || startDate.isBefore(LocalDateTime.now());
    }

    // 공간 예약 최대 시간 제한 (2시간)
    public boolean validateSpaceReservationTime(ReservationRequest request){
        LocalTime startTime = LocalTime.parse(request.getStartTime());
        LocalTime endTime = LocalTime.parse(request.getEndTime());

        Duration duration = Duration.between(startTime, endTime);
        return duration.toHours() <= 2;
    }

    // 당일권은 공간 예약 최대 3개, 자율좌석 1개
    // 30일 패스는 지정석 1개, 자율좌석 5개까지, 공간예약 ?
//    public boolean validateMembershipAndReservationType(IndividualMembershipRequest request){
//
//    }


    // 공간예약 가격 검증
    //
//    public boolean validateReservationPrice(ReservationRequest request){
//
//    }
}
