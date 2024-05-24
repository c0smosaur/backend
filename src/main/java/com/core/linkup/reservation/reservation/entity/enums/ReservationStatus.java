package com.core.linkup.reservation.reservation.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReservationStatus {

    RESERVED("예약 중"),
    OVER("예약 만료"),
    CANCELED("예약 취소");

    private final String description;
}
