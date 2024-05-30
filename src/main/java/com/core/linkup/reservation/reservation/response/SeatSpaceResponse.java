package com.core.linkup.reservation.reservation.response;

import com.core.linkup.office.entity.enums.SeatSpaceType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SeatSpaceResponse {
    private Long id;
    private SeatSpaceType type;
    private String code;
    private boolean isReserved;
}
