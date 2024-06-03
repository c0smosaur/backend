package com.core.linkup.office.request;

import lombok.Builder;

@Builder
public record OfficeSearchRequest (
        String city
) {
}


