package com.core.linkup.reservation.reservation.repository;


import com.core.linkup.office.entity.SeatSpace;
import com.querydsl.core.Tuple;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepositoryCustom {

    // 사용자의 전체 개인 멤버십/예약/좌석 조회
    List<Tuple> findAllIndividualMembershipAndReservationsAndSeatByMemberId(Long memberId);

    // 사용자의 해당 지점 전체 개인 멤버십/예약/좌석 조회
    List<Tuple> findAllReservationsAndSeatAndIndividualMembershipByMemberIdAndOfficeId(Long memberId, Long officeId);

    // 특정 개인 멤버십의 전체 예약/좌석
    List<Tuple> findAllReservationAndSeatByIndividualMembershipId(Long membershipId, Long memberId);

    // 개별 예약 조회
    Tuple findReservationAndSeatByReservationIdAndMembershipId(Long memberId, Long membershipId, Long reservationId);

    // 기업 멤버십의 예약 목록 조회
    List<Tuple> findAllReservationsAndSeatByCompanyMembershipId(Long membershipId, Long memberId);

    // 기업 멤버십 단일 예약 조회
    Tuple findReservationAndSeatByReservationIdAndCompanyMembershipId(Long memberId, Long companyMembershipId, Long reservationId);

    // 잔여 좌석 조회
    List<SeatSpace> findAllSeatSpacesByOfficeIdAndType(Long officeId, String type, LocalDateTime start, LocalDateTime end);
}
