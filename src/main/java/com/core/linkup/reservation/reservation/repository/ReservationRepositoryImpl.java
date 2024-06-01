package com.core.linkup.reservation.reservation.repository;

import com.core.linkup.member.entity.QMember;
import com.core.linkup.office.entity.QOfficeBuilding;
import com.core.linkup.office.entity.QSeatSpace;
import com.core.linkup.office.entity.SeatSpace;
import com.core.linkup.office.entity.enums.SeatSpaceType;
import com.core.linkup.reservation.membership.company.entity.QCompanyMembership;
import com.core.linkup.reservation.membership.individual.entity.QIndividualMembership;
import com.core.linkup.reservation.reservation.entity.QReservation;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    // 개인멤버십, 예약
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
    public List<Tuple> findAllReservationsAndSeatAndIndividualMembershipByMemberIdAndOfficeId(
            Long memberId, Long officeId){
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

    // 단일 개인 멤버십 예약 전체 조회
    @Override
    public List<Tuple> findAllReservationAndSeatByIndividualMembershipId(
            Long membershipId, Long memberId){
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

    // 개인 멤버십 개별 예약 조회
    @Override
    public Tuple findReservationAndSeatByReservationIdAndMembershipId(
            Long memberId, Long membershipId, Long reservationId){
        QIndividualMembership qIndividualMembership = QIndividualMembership.individualMembership;
        QReservation qReservation = QReservation.reservation;
        QSeatSpace qSeatSpace = QSeatSpace.seatSpace;

        return jpaQueryFactory.select(qReservation, qSeatSpace)
                .from(qReservation)
                .join(qSeatSpace).on(qReservation.seatId.eq(qSeatSpace.id))
                .join(qIndividualMembership).on(qReservation.individualMembershipId.eq(qIndividualMembership.id))
                .where(qReservation.id.eq(reservationId))
                .where(qIndividualMembership.id.eq(membershipId))
                .where(qIndividualMembership.memberId.eq(memberId))
                .fetchOne();
    }

    // 단일 기업 멤버십 예약 전체 조회
    @Override
    public List<Tuple> findAllReservationsAndSeatByCompanyMembershipId(
            Long membershipId, Long memberId) {
        QCompanyMembership qCompanyMembership = QCompanyMembership.companyMembership;
        QReservation qReservation = QReservation.reservation;
        QSeatSpace qSeatSpace = QSeatSpace.seatSpace;
        QMember qMember = QMember.member;

        return jpaQueryFactory.select(qReservation, qSeatSpace)
                .from(qReservation)
                .join(qCompanyMembership).on(qReservation.companyMembershipId.eq(qCompanyMembership.id))
                .join(qSeatSpace).on(qReservation.seatId.eq(qSeatSpace.id))
                .join(qMember).on(qMember.companyMembershipId.eq(membershipId))
                .where(qCompanyMembership.id.eq(membershipId).and(qMember.id.eq(memberId)))
                .fetch();
    }

    // 기업 멤버십 예약 개별 조회
    @Override
    public Tuple findReservationAndSeatByReservationIdAndCompanyMembershipId(
            Long memberId, Long membershipId, Long reservationId) {
        QCompanyMembership qCompanyMembership = QCompanyMembership.companyMembership;
        QReservation qReservation = QReservation.reservation;
        QSeatSpace qSeatSpace = QSeatSpace.seatSpace;
        QMember qMember = QMember.member;

        return jpaQueryFactory.select(qReservation, qSeatSpace)
                .from(qReservation)
                .join(qSeatSpace).on(qReservation.seatId.eq(qSeatSpace.id))
                .join(qCompanyMembership).on(qReservation.companyMembershipId.eq(qCompanyMembership.id))
                .join(qMember).on(qMember.id.eq(memberId))
                .where(qReservation.id.eq(reservationId))
//                .where(qCompanyMembership.id.eq(membershipId))
//                .where(qMember.id.eq(memberId))
                .fetchOne();
    }

    // 잔여 좌석 조회
    @Override
    public List<SeatSpace> findAllSeatSpacesByOfficeIdAndType(
            Long officeId, String type, LocalDateTime start, LocalDateTime end) {

        QSeatSpace qSeatSpace = QSeatSpace.seatSpace;
        QReservation qReservation = QReservation.reservation;

        List<Long> reservedSeatSpaceIds = jpaQueryFactory.select(qReservation.seatId)
                .from(qReservation)
                .where(qReservation.startDate.loe(end)
                        .and(qReservation.endDate.goe(start)))
                .fetch();

        return jpaQueryFactory.selectFrom(qSeatSpace)
                .where(qSeatSpace.officeBuilding.id.eq(officeId)
                        .and(qSeatSpace.type.eq(SeatSpaceType.valueOf(type)))
                        .and(qSeatSpace.id.notIn(reservedSeatSpaceIds)))
                .fetch();
    }

}

