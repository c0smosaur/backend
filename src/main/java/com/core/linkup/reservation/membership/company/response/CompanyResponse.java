package com.core.linkup.reservation.membership.company.response;

import lombok.Builder;

@Builder
public class CompanyResponse {

    private Long id;
    private String name;
    private String managerPhone;
    private String managerEmail;
}
