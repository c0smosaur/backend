package com.core.linkup.common.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ClubType{

    SPORTS("운동/스포츠"),
    SKILL_IMPROVEMENT("직무계발"),
    LINGUISTICS("외국어"),
    CULTURE_ART("문화/예술"),
    TRAVEL("여행"),
    COMMUNITY_SERVICE("봉사활동"),
    MEDIA("미디어 관람"),
    FINANCE("경제/재테크"),
    ETC("기타");

    private final String clubCategoryName;

    public static ClubType fromKor(String clubCategoryName) {
        for (ClubType type : ClubType.values()) {
            if (type.getClubCategoryName().equals(clubCategoryName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No matching occupation type for: " + clubCategoryName);
    }
}
