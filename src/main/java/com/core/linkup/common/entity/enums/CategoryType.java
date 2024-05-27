package com.core.linkup.common.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoryType {
    SPORTS("운동/스포츠"),
    CAREER_DEVELOPMENT("직무계발"),
    LANGUAGE("외국어"),
    CULTURE_ART("문화/예술"),
    TRAVEL("여행"),
    VOLUNTEERING("봉사활동"),
    MEDIA("미디어관람"),
    ECONOMICS("경제/재태크"),
    OTHER("기타");

    private final String inCategoryInKor;

    public static CategoryType fromKor(String inCategoryInKor) {
        for (CategoryType type : CategoryType.values()) {
            if (type.getInCategoryInKor().equals(inCategoryInKor)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No matching occupation type for: " + inCategoryInKor);
    }
}
