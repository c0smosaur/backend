package com.core.linkup.reservation.reservation.service;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.member.entity.Member;
import com.core.linkup.office.entity.OfficeBuilding;
import com.core.linkup.office.repository.OfficeRepository;
import com.core.linkup.reservation.membership.company.entity.CompanyMembership;
import com.core.linkup.reservation.membership.individual.entity.IndividualMembership;
import com.core.linkup.reservation.membership.individual.entity.enums.MembershipType;
import com.core.linkup.reservation.reservation.converter.ReservationConverter;
import com.core.linkup.reservation.reservation.entity.enums.ReservationType;
import com.core.linkup.reservation.reservation.repository.ReservationRepository;
import com.core.linkup.reservation.reservation.request.IndividualMembershipRegistrationRequest;
import com.core.linkup.reservation.reservation.request.ReservationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationValidationService {

    private final OfficeRepository officeRepository;
    private final ReservationConverter reservationConverter;
    private final ReservationRepository reservationRepository;

    // (검증) 개인 멤버십 요청의 지점과 전달받은 지점 일치하는지 검증
    public void validateOfficeLocation(IndividualMembershipRegistrationRequest requests, Long officeId) {
        OfficeBuilding officeBuilding = officeRepository.findFirstById(officeId);
        if (!requests.getMembership().getLocation().equals(officeBuilding.getLocation())) {
            throw new BaseException(BaseResponseStatus.INVALID_OFFICE_LOCATION);
        }
    }

    public void validateDateAndDuplicateReservation(
            ReservationRequest request){
        validateDuplicateReservation(request);
        validateReservationDate(request);
    }

    // 사용자 검증(개인 멤버십)
    public void validateMember(Member member, IndividualMembership membership){
        if (!member.getId().equals(membership.getMemberId())){
            throw new BaseException(BaseResponseStatus.INVALID_REQUEST);
        }
    }

    // 사용자 검증(기업 멤버십)
    public void validateMember(Member member, CompanyMembership membership){
        if (!member.getCompanyMembershipId().equals(membership.getId())){
            throw new BaseException(BaseResponseStatus.INVALID_REQUEST);
        }
    }

    // 사용자 검증(기업 멤버십, 기업 멤버십 id로)
    public void validateMember(Member member, Long companyMembershipId){
        if (!member.getCompanyMembershipId().equals(companyMembershipId)){
            throw new BaseException(BaseResponseStatus.INVALID_REQUEST);
        }
    }

    // 중복 예약 검증
    public void validateDuplicateReservation(ReservationRequest request){
        // startDate, startTime, endDate, endTime 찾아서 가져옴
        List<LocalDateTime> dates = reservationConverter.getLocalDateTime(request);
        LocalDateTime startDate = dates.get(0);
        LocalDateTime endDate = dates.get(1);

        // 해당 좌석에 해당 날짜로 예약이 있는지 파악
        // 있으면 예외
        if (reservationRepository.existsByIdAndStartDateAndEndDate(request.getSeatId(), startDate, endDate)){
            throw new BaseException(BaseResponseStatus.DUPLICATE_RESERVATION);
        }
    }

    // 예약 날짜 검증
    public void validateReservationDate(ReservationRequest request){
        // 검증 통과 x 사례
        // 1) 시작일자보다 종료일자가 빠른 경우
        // 2) 종료일자가 현재 일자보다 빠른 경우
        // 3) 시작일자가 현재 날짜보다 빠른 경우
        List<LocalDateTime> dates = reservationConverter.getLocalDateTime(request);
        LocalDateTime startDate = dates.get(0);
        LocalDateTime endDate = dates.get(1);

            if (!startDate.isBefore(endDate)
                    || endDate.isBefore(LocalDateTime.now())
                    || startDate.isBefore(LocalDateTime.now())){
                throw new BaseException(BaseResponseStatus.INVALID_RESERVATION_DATE);
            }
    }

    // 공간 예약 최대 시간 제한 (2시간)
    public boolean validateSpaceReservationTime(ReservationRequest request){
        LocalTime startTime = LocalTime.parse(request.getStartTime());
        LocalTime endTime = LocalTime.parse(request.getEndTime());

        Duration duration = Duration.between(startTime, endTime);
        return duration.toHours() <= 2;
    }

    // 개인 멤버십 생성
    // 당일권은 공간 예약 최대 3개, 자율좌석 1개
    // 30일 패스는 지정석 1개, 자율좌석 5개까지
    public void validateIndividualMembershipAndReservationType(IndividualMembershipRegistrationRequest request){
        int designated = 0;
        int temporary = 0;
        int space = 0;

        MembershipType membershipType = MembershipType.fromKor(request.getMembership().getType());
        List<ReservationRequest> reservations = request.getReservations();
        for (ReservationRequest reservation : reservations) {
            ReservationType reservationType = ReservationType.fromKor(reservation.getType());

            switch (reservationType){
                case TEMPORARY_SEAT -> temporary += 1;
                case DESIGNATED_SEAT -> designated += 1;
                case SPACE -> space += 1;
                default -> throw new BaseException(BaseResponseStatus.INVALID_REQUEST);
            }
        }

        if (membershipType.equals(MembershipType.ONE_DAY_PASS)){
            // 1일 패스
            if (designated > 0 || temporary < 0 || temporary > 5 || space > 3){
                throw new BaseException(BaseResponseStatus.INVALID_REQUEST);
            }
        } else {
            // 30일 패스
            if (designated < 0 || designated > 1){
                throw new BaseException(BaseResponseStatus.INVALID_REQUEST);
            }
        }
    }

    // 예약 추가 시
    public void validateReservationRequests(
            List<ReservationRequest> requests, MembershipType membershipType){
        int companyDesignated = 0;
        int designated = 0;
        int temporary = 0;
        int space = 0;

        for (ReservationRequest request : requests) {
            ReservationType reservationType = ReservationType.fromKor(request.getType());

            switch (reservationType){
                case COMPANY_DESIGNATED_SEAT -> companyDesignated += 1;
                case TEMPORARY_SEAT -> temporary += 1;
                case DESIGNATED_SEAT -> designated += 1;
                case SPACE -> space += 1;
                default -> throw new BaseException(BaseResponseStatus.INVALID_REQUEST);
            }
        }

        if (membershipType.equals(MembershipType.ONE_DAY_PASS)){

        }

    }


    // 공간예약 가격 검증
    //
//    public boolean validateReservationPrice(ReservationRequest request){
//
//    }

    // 기업 문의 가격 검증

}
