package com.core.linkup.reservation.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReservationType {

    DESIGNATED_SEAT("지정석"),
    TEMPORARY_SEAT("자율 좌석"),
    SPACE("공간");

    private final String description;

}
