package com.core.linkup.reservation.reservation.request;

import com.core.linkup.reservation.membership.company.request.CompanyMembershipRequest;
import com.core.linkup.reservation.membership.company.request.CompanyRequest;
import lombok.Getter;

@Getter
public class CompanyMembershipRegistrationRequest {
    private CompanyRequest company;
    private CompanyMembershipRequest companyMembership;
}
