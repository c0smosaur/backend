package com.core.linkup.office.response;

import lombok.Builder;

@Builder
public record OfficeSearchResponse(
        Long id,
        String location,
        String region,
        String city,
        String street,
        String address,
        Integer capacity,
        Integer openHours,
        String officePhone,
        String trafficInfo,
        Double latitude,
        Double longitude,
        String images,
        OfficeDetailSearchResponse officeDetail
) {

}
