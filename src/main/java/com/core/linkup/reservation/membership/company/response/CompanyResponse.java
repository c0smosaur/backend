package com.core.linkup.reservation.membership.company.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CompanyResponse {

    private Long id;
    private String name;
    private String managerPhone;
    private String managerEmail;
}
