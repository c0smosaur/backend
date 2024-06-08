package com.core.linkup.reservation.reservation.service;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.member.entity.Member;
import com.core.linkup.reservation.membership.company.converter.CompanyMembershipConverter;
import com.core.linkup.reservation.membership.company.entity.CompanyMembership;
import com.core.linkup.reservation.membership.company.repository.CompanyMembershipRepository;
import com.core.linkup.reservation.membership.company.service.CompanyMembershipService;
import com.core.linkup.reservation.reservation.converter.ReservationConverter;
import com.core.linkup.reservation.reservation.entity.Reservation;
import com.core.linkup.reservation.reservation.entity.enums.ReservationStatus;
import com.core.linkup.reservation.reservation.repository.ReservationRepository;
import com.core.linkup.reservation.reservation.request.CompanyMembershipRegistrationRequest;
import com.core.linkup.reservation.reservation.request.ReservationRequest;
import com.core.linkup.reservation.reservation.response.*;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyMembershipReservationService {

    private final CompanyMembershipRepository companyMembershipRepository;
    private final ReservationRepository reservationRepository;

    private final CompanyMembershipService companyMembershipService;
    private final ReservationService reservationService;

    private final ReservationConverter reservationConverter;
    private final CompanyMembershipConverter companyMembershipConverter;

    // (생성, 응답 변환) 기업 문의하기 -> 기업, 기업 멤버십 생성
    public CompanyMembershipRegistrationResponse registerCompanyMembership(CompanyMembershipRegistrationRequest request) {
        return companyMembershipService.registerCompanyMembership(request);
    }

    // 사용자의 단일 기업 멤버십
    public MembershipResponse getCompanyMembership(Member member) {
        if (member.getCompanyMembershipId()==null){
//            return reservationConverter.emptyMembershipResponse();
            return null;
        }
        return companyMembershipConverter.toMembershipResponse(
                companyMembershipRepository.findFirstById(member.getCompanyMembershipId()));
    }

    // 사용자의 단일 기업 멤버십 + 예약 전체 조회
    // null이면 빈 리스트
    public MembershipReservationListResponse getReservationsForCompanyMembership(Member member){
        if (member.getCompanyMembershipId()==null){
            return null;
        }
        Optional<CompanyMembership> companyMembership =
                companyMembershipRepository.findById(member.getCompanyMembershipId());
        if (companyMembership.isPresent()) {
            List<ReservationResponse> reservationResponses =
                    reservationService.getReservationResponsesWithMembership(member, companyMembership.get());

            return reservationConverter.toMembershipReservationListResponse(
                    companyMembershipConverter.toMembershipResponse(companyMembership.get()),
                    reservationResponses);
        } else {
            return reservationConverter.emptyMembershipReservationListResponse();
        }
    }

    // 기업 멤버십 예약 개별 조회
    // null이면 404
    public ReservationResponse getReservationForCompanyMembership(
            Member member, Long membershipId, Long reservationId){
        CompanyMembership companyMembership = companyMembershipRepository.findFirstById(membershipId);
        return reservationService.getReservationResponseForMembership(member, companyMembership, reservationId);
    }

    // 기업 멤버십 예약 추가 생성
    public MembershipReservationListResponse addCompanyReservations(
            Long membershipId, List<ReservationRequest> requests){
        CompanyMembership companyMembership =
                companyMembershipRepository.findFirstById(membershipId);
        List<ReservationResponse> reservationResponses =
                reservationService.createReservationResponses(requests, companyMembership);
        return reservationConverter.toMembershipReservationListResponse(
                companyMembershipConverter.toMembershipResponse(companyMembership),
                reservationResponses);
    }

    // (수정) 기업 멤버십 지정석 수정
    public ReservationResponse updateReservation(ReservationRequest request,
                                                           Long reservationId,
                                                           Long membershipId) {

        Reservation reservation = reservationRepository.findFirstById(reservationId);
        CompanyMembership companyMembership = companyMembershipRepository.findFirstById(membershipId);

        return reservationService.updateReservationByType(request, reservation, companyMembership);
    }

    // (삭제) 개별 예약 삭제
    public boolean deleteReservationForCompanyMembership(Member member, Long membershipId, Long reservationId) {
        Reservation reservation = reservationRepository.findFirstById(reservationId);
        if (member.getCompanyMembershipId().equals(membershipId)){
            reservation.setStatus(ReservationStatus.CANCELED);
            return true;
        } else {
            throw new BaseException(BaseResponseStatus.INVALID_REQUEST);
        }
    }

    // 입력된 날짜의 예약 반환
    public List<MainPageReservationResponse> getReservationsForCompanyMembershipOnDate(
            Member member, LocalDate date) {
        List<Tuple> tuples =
                reservationRepository.findAllReservationsAndSeatForCompanyMembershipByMemberIdAndDate(
                        member.getId(), date);
        return reservationService.getMainPageReservationResponseFromTuple(tuples);
    }
}
