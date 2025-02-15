package com.core.linkup.common.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IndustryType {
    IT("IT"),
    CONSTRUCTION("건설"),
    CONSULTATION("컨설팅"),
    MARKETING("마케팅"),
    COMMERCE("무역"),
    FINANCING("금융"),
    MEDICAL("의료"),
    EDUCATION("교육"),
    MEDIA("미디어"),
    LOGISTICS("물류"),
    REAL_ESTATE("부동산"),
    FOOD_AND_BEVERAGE("F&B"),
    ET_CETERA("기타");

    private final String industryName;

    public static IndustryType fromKor(String industryName) {
        for (IndustryType type : values()) {
            if (type.getIndustryName().equals(industryName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No matching occupation type for: " + industryName);
    }
}
