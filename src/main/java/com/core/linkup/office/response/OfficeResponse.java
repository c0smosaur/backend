package com.core.linkup.office.response;

public record OfficeResponse(
        Long id,
        String location,
        String region,
        String city,
        String street,
        String address,
        Integer capacity,
        String trafficInfo,
        Double latitude,
        Double longitude,
        String images
) {

}
