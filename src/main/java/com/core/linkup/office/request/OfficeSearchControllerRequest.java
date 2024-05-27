package com.core.linkup.office.request;

public record OfficeSearchControllerRequest(
        String region,
        String city,
        String street,
        String industry,
        String occupation
){
}
