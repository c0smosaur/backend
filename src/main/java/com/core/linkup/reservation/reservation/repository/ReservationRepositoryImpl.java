package com.core.linkup.reservation.reservation.repository;

import com.core.linkup.office.entity.QOfficeBuilding;
import com.core.linkup.office.entity.QSeatSpace;
import com.core.linkup.reservation.membership.individual.entity.QIndividualMembership;
import com.core.linkup.reservation.reservation.entity.QReservation;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Tuple> findAllIndividualMembershipAndReservationsAndSeatByMemberId(Long memberId){
        QIndividualMembership qIndividualMembership = QIndividualMembership.individualMembership;
        QReservation qReservation = QReservation.reservation;
        QSeatSpace qSeatSpace = QSeatSpace.seatSpace;

        return jpaQueryFactory.select(qIndividualMembership, qReservation, qSeatSpace)
                .from(qIndividualMembership)
                .join(qReservation).on(qIndividualMembership.id.eq(qReservation.individualMembershipId))
                .join(qSeatSpace).on(qReservation.seatId.eq(qSeatSpace.id))
                .where(qIndividualMembership.memberId.eq(memberId))
                .fetch();
    }

    // 해당 지점 개인 멤버십 예약 전체 조회
    @Override
    public List<Tuple> findAllReservationsAndSeatAndIndividualMembershipByMemberIdAndOfficeId(Long memberId, Long officeId){
        QOfficeBuilding qOfficeBuilding = QOfficeBuilding.officeBuilding;
        QIndividualMembership qIndividualMembership = QIndividualMembership.individualMembership;
        QReservation qReservation = QReservation.reservation;
        QSeatSpace qSeatSpace = QSeatSpace.seatSpace;

        return jpaQueryFactory.select(qIndividualMembership, qReservation, qSeatSpace)
                .from(qIndividualMembership)
                .join(qReservation).on(qIndividualMembership.id.eq(qReservation.individualMembershipId))
                .join(qSeatSpace).on(qReservation.seatId.eq(qSeatSpace.id))
                .where(qIndividualMembership.memberId.eq(memberId))
                .where(qIndividualMembership.location.eq(qOfficeBuilding.location))
                .fetch();
    }

    // 특정 개인 멤버십 예약 전체 조회
    @Override
    public List<Tuple> findAllReservationAndSeatByMembershipId(Long membershipId, Long memberId){
        QIndividualMembership qIndividualMembership = QIndividualMembership.individualMembership;
        QReservation qReservation = QReservation.reservation;
        QSeatSpace qSeatSpace = QSeatSpace.seatSpace;

        return jpaQueryFactory.select(qReservation, qSeatSpace)
                .from(qReservation)
                .join(qIndividualMembership).on(qReservation.individualMembershipId.eq(qIndividualMembership.id))
                .join(qSeatSpace).on(qReservation.seatId.eq(qSeatSpace.id))
                .where(qIndividualMembership.id.eq(membershipId).and(qIndividualMembership.memberId.eq(memberId)))
                .fetch();
    }

    // 개별 예약 조회
    public List<Tuple> findReservationAndSeatByReservationId(Long reservationId, Long membershipId,
                                                             Long memberId){
        QIndividualMembership qIndividualMembership = QIndividualMembership.individualMembership;
        QReservation qReservation = QReservation.reservation;
        QSeatSpace qSeatSpace = QSeatSpace.seatSpace;

        return jpaQueryFactory.select(qReservation, qSeatSpace)
                .from(qReservation)
                .join(qReservation).on(qReservation.seatId.eq(qSeatSpace.id))
                .where(qReservation.id.eq(reservationId))
                .where(qReservation.individualMembershipId.eq(membershipId))
                .where(qIndividualMembership.memberId.eq(memberId))
                .fetch();
    }


}

