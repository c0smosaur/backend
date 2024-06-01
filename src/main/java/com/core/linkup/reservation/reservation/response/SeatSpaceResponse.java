package com.core.linkup.reservation.reservation.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SeatSpaceResponse {
    private Long id;
    private String type;
    private String code;
    private boolean isAvailable;
}
