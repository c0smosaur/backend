package com.core.linkup.reservation.reservation.repository;

import com.core.linkup.member.entity.QMember;
import com.core.linkup.office.entity.QSeatSpace;
import com.core.linkup.office.entity.SeatSpace;
import com.core.linkup.office.entity.enums.SeatSpaceType;
import com.core.linkup.reservation.membership.company.entity.QCompanyMembership;
import com.core.linkup.reservation.membership.individual.entity.QIndividualMembership;
import com.core.linkup.reservation.reservation.entity.QReservation;
import com.core.linkup.reservation.reservation.entity.Reservation;
import com.core.linkup.reservation.reservation.entity.enums.ReservationStatus;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Override
    public List<Tuple> findAllReservationsAndSeatForIndividualMembershipByMemberIdAndDate(
            Long memberId, LocalDate date){
        QIndividualMembership qIndividualMembership = QIndividualMembership.individualMembership;
        QReservation qReservation = QReservation.reservation;
        QSeatSpace qSeatSpace = QSeatSpace.seatSpace;

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        return jpaQueryFactory.select(qIndividualMembership, qReservation, qSeatSpace)
                .from(qIndividualMembership)
                .join(qReservation).on(qIndividualMembership.id.eq(qReservation.individualMembershipId))
                .join(qSeatSpace).on(qReservation.seatId.eq(qSeatSpace.id))
                .where(qIndividualMembership.memberId.eq(memberId)
                        .and(qReservation.startDate.loe(endOfDay))
                        .and(qReservation.endDate.goe(startOfDay))
                        .and(qReservation.status.ne(ReservationStatus.CANCELED)))
                .fetch();
    }

    @Override
    public List<Tuple> findAllReservationsAndSeatForCompanyMembershipByMemberIdAndDate(
            Long memberId, LocalDate date){
        QCompanyMembership qCompanyMembership = QCompanyMembership.companyMembership;
        QReservation qReservation = QReservation.reservation;
        QSeatSpace qSeatSpace = QSeatSpace.seatSpace;
        QMember qMember = QMember.member;

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        return jpaQueryFactory.select(qCompanyMembership, qReservation, qSeatSpace)
                .from(qCompanyMembership)
                .join(qMember).on(qCompanyMembership.id.eq(qMember.companyMembershipId))
                .join(qReservation).on(qCompanyMembership.id.eq(qReservation.companyMembershipId))
                .join(qSeatSpace).on(qReservation.seatId.eq(qSeatSpace.id))
                .where(qMember.id.eq(memberId)
                        .and(qReservation.startDate.loe(endOfDay))
                        .and(qReservation.endDate.goe(startOfDay))
                        .and(qReservation.status.ne(ReservationStatus.CANCELED)))
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
                .where(qReservation.status.eq(ReservationStatus.RESERVED))
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
                .where(qReservation.status.eq(ReservationStatus.RESERVED))
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
                .where(qReservation.status.eq(ReservationStatus.RESERVED))
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
                .where(qReservation.status.eq(ReservationStatus.RESERVED))
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
                .join(qSeatSpace).on(qReservation.seatId.eq(qSeatSpace.id))
                .where(qReservation.startDate.loe(end)
                        .and(qReservation.endDate.goe(start))
                        .and(qReservation.status.eq(ReservationStatus.RESERVED))
                        .and(qSeatSpace.type.eq(SeatSpaceType.valueOf(type))))
                .fetch();

        return jpaQueryFactory.selectFrom(qSeatSpace)
                .where(qSeatSpace.officeBuildingId.eq(officeId)
                        .and(qSeatSpace.type.eq(SeatSpaceType.valueOf(type)))
                        .and(qSeatSpace.id.notIn(reservedSeatSpaceIds)))
                .fetch();
    }

    // 잔여 공간 조회
    @Override
    public List<Reservation> findAllReservationsBySeatIdAndDateAndType(
            Long seatId, LocalDateTime startDate, SeatSpaceType type) {

        QReservation qReservation = QReservation.reservation;
        QSeatSpace qSeatSpace = QSeatSpace.seatSpace;

        BooleanExpression datePredicate = qReservation.startDate.between(
                startDate.toLocalDate().atStartOfDay(),
                startDate.toLocalDate().atTime(LocalTime.MAX)
        );

        return jpaQueryFactory.selectFrom(qReservation)
                .join(qSeatSpace).on(qReservation.seatId.eq(qSeatSpace.id))
                .where(qReservation.seatId.eq(seatId)
                        .and(datePredicate)
                        .and(qSeatSpace.type.eq(type))
                        .and(qReservation.status.eq(ReservationStatus.RESERVED)))
                .fetch();
    }

    @Override
    public Tuple findMostRecentIndividualMembershipAndReservationAndSeatSpace(Long memberId){
        QIndividualMembership qIndividualMembership = QIndividualMembership.individualMembership;
        QReservation qReservation = QReservation.reservation;
        QSeatSpace qSeatSpace = QSeatSpace.seatSpace;

        LocalDateTime today = LocalDateTime.now();

        return jpaQueryFactory.select(qIndividualMembership, qReservation, qSeatSpace)
                .from(qIndividualMembership)
                .join(qReservation).on(qReservation.individualMembershipId.eq(qIndividualMembership.id))
                .join(qSeatSpace).on(qReservation.seatId.eq(qSeatSpace.id))
                .where(qIndividualMembership.memberId.eq(memberId)
                        .and(qReservation.endDate.before(today))) // Ensure the reservation has passed
                .orderBy(qReservation.endDate.desc()) // Order by the closest endDate in the past
                .limit(1) // Limit to one result
                .fetchOne();
    }

    @Override
    public Tuple findMostRecentCompanyMembershipAndReservationAndSeatSpace(Long memberId){
        QCompanyMembership qCompanyMembership = QCompanyMembership.companyMembership;
        QReservation qReservation = QReservation.reservation;
        QSeatSpace qSeatSpace = QSeatSpace.seatSpace;
        QMember qMember = QMember.member;

        LocalDateTime today = LocalDateTime.now();

        return jpaQueryFactory.select(qCompanyMembership, qReservation, qSeatSpace)
                .from(qMember)
                .join(qCompanyMembership).on(qMember.companyMembershipId.eq(qCompanyMembership.id))
                .join(qReservation).on(qReservation.companyMembershipId.eq(qCompanyMembership.id))
                .join(qSeatSpace).on(qReservation.seatId.eq(qSeatSpace.id))
                .where(qCompanyMembership.id.eq(qMember.companyMembershipId)
                        .and(qReservation.endDate.before(today)))
                .orderBy(qReservation.endDate.desc())
                .limit(1)
                .fetchOne();

    }
}

