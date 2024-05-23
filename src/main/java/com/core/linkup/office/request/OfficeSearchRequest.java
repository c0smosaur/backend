package com.core.linkup.office.request;

import com.core.linkup.office.entity.OfficeDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
public class OfficeSearchRequest {

    private final String region;
    private final String city;
    private final String street;
    private final OfficeDetail industry;
    private final String occupation;

    public OfficeSearchRequest(OfficeSearchRequest request) {
        this.region = request.region;
        this.city = request.city;
        this.street = request.street;
        this.industry = request.industry;
        this.occupation = request.occupation;
    }
}
