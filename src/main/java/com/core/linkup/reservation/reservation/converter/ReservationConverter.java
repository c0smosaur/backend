package com.core.linkup.reservation.reservation.converter;

import com.core.linkup.common.annotation.Converter;
import com.core.linkup.reservation.membership.company.response.CompanyResponse;
import com.core.linkup.reservation.membership.individual.response.IndividualMembershipResponse;
import com.core.linkup.reservation.reservation.entity.Reservation;
import com.core.linkup.reservation.reservation.response.CompanyMembershipRegistrationResponse;
import com.core.linkup.reservation.reservation.response.IndividualMembershipRegistrationResponse;
import com.core.linkup.reservation.reservation.response.ReservationResponse;

import java.util.List;

@Converter
public class ReservationConverter {

    public CompanyMembershipRegistrationResponse toCompanyRegistrationResponse(
            CompanyResponse company,
            com.core.linkup.reservation.membership.company.response.CompanyMembershipResponse companyMembership) {

        return CompanyMembershipRegistrationResponse.builder()
                .company(company)
                .companyMembership(companyMembership)
                .build();
    }

    public IndividualMembershipRegistrationResponse toIndividualRegistrationResponse(
            IndividualMembershipResponse membership,
            List<ReservationResponse> reservations){

        return IndividualMembershipRegistrationResponse.builder()
                .membership(membership)
                .reservations(reservations)
                .build();
    }

    public ReservationResponse toReservationResponse(
            Reservation reservation){
        return ReservationResponse.builder()
                .type(reservation.getType().getDescription())
                .startDate(reservation.getStartDate())
                .endDate(reservation.getEndDate())
                .price(reservation.getPrice())
                .seatType(reservation.getSeatSpace().getType())
                .seatCode(reservation.getSeatSpace().getCode())
                .build();
    }
}
