package com.core.linkup.reservation.reservation.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReservationType {

    COMPANY_DESIGNATED_SEAT("기업 지정석"),
    DESIGNATED_SEAT("지정석"),
    TEMPORARY_SEAT("자율 좌석"),
    SPACE("공간");

    private final String name;
    public static ReservationType fromKor(String name) {
        for (ReservationType type : ReservationType.values()) {
            if (type.getName().equals(name.trim())) {
                return type;
            }
        }
        throw new IllegalArgumentException("No matching occupation type for: " + name);
    }
}