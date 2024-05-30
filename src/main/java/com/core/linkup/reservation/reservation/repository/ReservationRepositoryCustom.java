package com.core.linkup.reservation.reservation.repository;


import com.querydsl.core.Tuple;

import java.util.List;

public interface ReservationRepositoryCustom {

    // 사용자의 전체 멤버십/예약/좌석
    List<Tuple> findAllIndividualMembershipAndReservationsAndSeatByMemberId(Long memberId);

    // 사용자의 해당 지점 전체 멤버십/예약/좌석
    List<Tuple> findAllReservationsAndSeatAndIndividualMembershipByMemberIdAndOfficeId(Long memberId, Long officeId);

    // 특정 멤버십의 전체 예약/좌석
    List<Tuple> findAllReservationAndSeatByMembershipId(Long membershipId, Long memberId);
}
