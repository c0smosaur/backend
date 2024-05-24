package com.core.linkup.common.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CityType {
    GANGNAM("강남"),
    SEOCHO("서초"),
    YEONGDEUNPO("영등포"),
    GWANAK("관악"),
    GURO("구로"),
    GANGSEO("강서"),
    JUNGGU("중구") ,
    JONGNO("종로"),
    YONGSAN("용산") ,
    SEONGDONG("성동"),
    MAPO("마포"),
    SEONGNAM("성남");

    private final String cityName;

    public static CityType fromCityName(String koreanName) {
        for (CityType cityType : values()) {
            if (cityType.getCityName().equals(koreanName)) {
                return cityType;
            }
        }
        throw new IllegalArgumentException("No enum constant for Korean name: " + koreanName);
    }
}
