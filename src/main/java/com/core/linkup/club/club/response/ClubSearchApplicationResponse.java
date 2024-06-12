package com.core.linkup.club.club.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ClubSearchApplicationResponse (
        Long clubId,
        String clubTitle,
        String clubIntroduction,
        String clubDetailIntroduction,
        Integer clubRecruitCount,
        String clubCategory,
        String clubThumbnail,
        Long memberId,
        String memberName,
        String memberProfileImage,
        Boolean approval,
        Boolean liked
) {
}
