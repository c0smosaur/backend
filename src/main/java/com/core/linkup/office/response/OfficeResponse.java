package com.core.linkup.office.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OfficeResponse {

    private Long id;
    private String location;
    private String region;
    private String city;
    private String street;
    private String address;
    private Integer capacity;
    private String trafficInfo;
    private Double latitude;
    private Double longitude;
    private String images;

}
