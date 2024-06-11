package com.core.linkup.club.club.response;

import lombok.Builder;
import lombok.Setter;

import java.util.List;

@Builder
public record ClubApplicationResponse (
        Long id, //clubMemberId
        Long clubId,
        Long memberId,
        String clubTitle,
        String clubIntroduction,
        Integer clubRecruitCount,
        String clubCategory,
        String clubThumbnail,
        Boolean approval,
        Boolean liked,
        List<ClubAnswerResponse> clubAnswer
) {
}
