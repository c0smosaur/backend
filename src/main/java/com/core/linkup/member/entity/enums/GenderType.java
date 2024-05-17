package com.core.linkup.member.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderType {

    MALE("남성"),
    FEMALE("여성"),
    NEITHER("선택 안함"),;

    private final String genderInKor;

    // TODO: enum 공통으로 뽑아내는 것도 고려
    public static GenderType fromKor(String genderInKor) {
        for (GenderType type : values()) {
            if (type.getGenderInKor().equals(genderInKor)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No matching occupation type for: " + genderInKor);
    }

}
