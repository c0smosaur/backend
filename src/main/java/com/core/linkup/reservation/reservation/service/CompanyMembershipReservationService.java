package com.core.linkup.reservation.reservation.service;

import com.core.linkup.office.repository.OfficeRepository;
import com.core.linkup.office.repository.SeatSpaceRepository;
import com.core.linkup.reservation.membership.company.repository.CompanyMembershipRepository;
import com.core.linkup.reservation.membership.company.service.CompanyMembershipService;
import com.core.linkup.reservation.reservation.converter.ReservationConverter;
import com.core.linkup.reservation.reservation.repository.ReservationRepository;
import com.core.linkup.reservation.reservation.request.CompanyMembershipRegistrationRequest;
import com.core.linkup.reservation.reservation.response.CompanyMembershipRegistrationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyMembershipReservationService {

    private final SeatSpaceRepository seatSpaceRepository;
    private final OfficeRepository officeRepository;
    private final ReservationRepository reservationRepository;
    private final CompanyMembershipRepository companyMembershipRepository;

    private final CompanyMembershipService companyMembershipService;
    private final ReservationService reservationService;

    private final ReservationConverter reservationConverter;

    // (생성, 응답 변환) 기업 문의하기 -> 기업, 기업 멤버십 생성
    public CompanyMembershipRegistrationResponse registerCompanyMembership(CompanyMembershipRegistrationRequest request) {
        return companyMembershipService.registerCompanyMembership(request);
    }
}
