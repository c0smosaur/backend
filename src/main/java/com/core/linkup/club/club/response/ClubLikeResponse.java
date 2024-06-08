package com.core.linkup.club.club.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ClubLikeResponse(
        Long id,
        Long clubId,
        Long memberId,
        String clubThumbnail,
        String clubName,
        String clubIntroduction,
        Integer clubMemberCount,
        LocalDate clubMeetingDate
) {
}
