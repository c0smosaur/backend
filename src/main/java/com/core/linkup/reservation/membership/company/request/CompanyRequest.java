package com.core.linkup.reservation.membership.company.request;

import lombok.Getter;

@Getter
public class CompanyRequest {
    private String name;
    private String managerPhone;
    private String managerEmail;
    private boolean consentContact;
    private boolean consentPromotion;
}
