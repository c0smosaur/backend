package com.core.linkup.reservation.reservation.response;

import com.core.linkup.reservation.membership.company.response.CompanyMembershipResponse;
import com.core.linkup.reservation.membership.company.response.CompanyResponse;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CompanyMembershipRegistrationResponse {

    private final CompanyResponse company;
    private final CompanyMembershipResponse companyMembership;
}
